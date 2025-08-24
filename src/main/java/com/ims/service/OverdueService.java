package com.ims.service;

import com.ims.model.Invoice;
import com.ims.model.Overdue;
import com.ims.repository.InvoiceRepository;
import com.ims.repository.OverdueRepository;
import com.ims.util.PaymentStatus;
import static com.ims.util.Utils.getUniqueId;
import static com.ims.util.Utils.getOverdueDate;
import static com.ims.util.Utils.getCurrentDateTime;
import static com.ims.util.Utils.getInitialAmount;

import com.ims.util.Utils;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class OverdueService {

    private final Logger log = Utils.getLogger(OverdueService.class);

    @Autowired
    private OverdueRepository overdueRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Overdue getOverdueById(String id) {
        return overdueRepository.findById(id).orElseThrow( () -> new RuntimeException("Overdue not found for id: " + id));
    }

    public List<Overdue> getAllOverdue() {
        return overdueRepository.findAll();
    }

    @Transactional
    public Overdue processOverdue(Overdue overdue) {

        Overdue addOverdue = addOverdue(overdue);

        List<Invoice> invoiceList = invoiceRepository.findAllByStatus(PaymentStatus.PENDING.getValue());
        log.debug("ProcessOverdue: invoiceList:{}", invoiceList);

        processVoidInvoice(overdue, invoiceList);
        processPendingInvoice(overdue,invoiceList);

        return addOverdue;
    }

    private Overdue addOverdue(Overdue overdue) {
        Overdue addOverdue = new Overdue();

        addOverdue.setOverdueId(getUniqueId());
        addOverdue.setOverdueDate(getCurrentDateTime());
        addOverdue.setOverdueDays(overdue.getOverdueDays());
        addOverdue.setLateFee(overdue.getLateFee());

        overdueRepository.save(addOverdue);

        log.debug("Overdue saved successfully, now processing the overdue...: {}", addOverdue);
        return addOverdue;
    }

    private void processVoidInvoice(Overdue overdue, List<Invoice> invoiceList) {
        List<Invoice> voidList = getFilteredInvoice(invoiceList,PaymentStatus.VOID.getValue());
        log.debug("ProcessVoidInvoice: voidList:{}", voidList);
        if(!voidList.isEmpty()) {
            //  If an invoice is not paid at all, the invoice should be marked as void, a new invoice should be
            //  created with the amount plus the late fee, and a new overdue.
            updateInvoiceStatus(voidList, PaymentStatus.VOID.getValue());
            processRecreateInvoice(overdue, voidList, PaymentStatus.VOID.getValue());
        }
    }


    private void processPendingInvoice(Overdue overdue,List<Invoice> invoiceList) {
        List<Invoice> pendingList = getFilteredInvoice(invoiceList,PaymentStatus.PENDING.getValue());
        log.debug("ProcessPendingInvoice: pendingList:{}", pendingList);
        if(!pendingList.isEmpty()){
            //If an invoice is partially paid, the invoice should be marked as paid, and a new invoice should be
            //created with the remaining amount plus the late fee, and a new overdue.
            updateInvoiceStatus(pendingList, PaymentStatus.PAID.getValue());
            processRecreateInvoice(overdue, pendingList, PaymentStatus.PENDING.getValue());
        }
    }

    private List<Invoice> getFilteredInvoice(List<Invoice> invoiceList,String status) {
        // Filter invoices based on the status
        if(status.equalsIgnoreCase(PaymentStatus.PENDING.getValue())) {
            return invoiceList.stream()
                    .filter(invoice ->
                            ((invoice.getPaidAmount() > 0
                            && invoice.getPaidAmount() < invoice.getInvoiceAmount())
                            && invoice.getStatus().equalsIgnoreCase(PaymentStatus.PENDING.getValue())))
                    .toList();
        } else if(status.equalsIgnoreCase(PaymentStatus.VOID.getValue())) {
            return invoiceList.stream()
                    .filter(invoice ->
                            (invoice.getPaidAmount() == 0
                            && invoice.getStatus().equalsIgnoreCase(PaymentStatus.PENDING.getValue())))
                    .toList();
        }
        return List.of();
    }

    private void updateInvoiceStatus(List<Invoice> invoiceList, String status) {
        if(!invoiceList.isEmpty()) {
            List<Invoice> updatedInvoiceList = invoiceList.stream()
                    .peek(invoice -> invoice.setStatus(status)).toList();

            log.debug("Update Invoice status:status:{}:invoiceList:{}", status, updatedInvoiceList);
            invoiceRepository.saveAll(updatedInvoiceList);
        }
    }

    private void processRecreateInvoice(Overdue overdue, List<Invoice> voidList, String status) {
        if(!voidList.isEmpty()){
            List<Invoice> reCreatedInvoiceList = voidList.stream()
                    .map(invoice -> {
                        Invoice reCreateInvoice = new Invoice();
                        reCreateInvoice.setInvoiceId(getUniqueId());
                        reCreateInvoice.setInvoiceDate(getCurrentDateTime());
                        reCreateInvoice.setInvoiceAmount(calculateInvoiceAmount(overdue,invoice,status));
                        reCreateInvoice.setPaidAmount(getInitialAmount());
                        reCreateInvoice.setDueDate(getOverdueDate(overdue.getOverdueDays()));
                        reCreateInvoice.setStatus(PaymentStatus.PENDING.getValue());
                        reCreateInvoice.setArchivedInvoiceId(invoice.getInvoiceId());
                        return reCreateInvoice;
                    }).toList();
            log.debug("ReCreated Invoice List:status:{}:invoiceList:{}", status, reCreatedInvoiceList);
            invoiceRepository.saveAll(reCreatedInvoiceList);
        }
    }

    private double calculateInvoiceAmount(Overdue overdue, Invoice invoice, String status) {
        double invoiceAmount;
        // Process the invoice amount based on the status
        if(status.equalsIgnoreCase(PaymentStatus.VOID.getValue())) {
            invoiceAmount = (invoice.getInvoiceAmount() + overdue.getLateFee());
        }
        else {
            invoiceAmount = (invoice.getInvoiceAmount() - invoice.getPaidAmount() + overdue.getLateFee());
        }
        log.debug("InvoiceAmount:{}", invoiceAmount);
        return invoiceAmount;
    }

    public void deleteOverdueById(String id) {
        overdueRepository.deleteById(id);
    }

    public void deleteAllOverdue() {
        overdueRepository.deleteAll();
    }
}
