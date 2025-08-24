package com.ims.service;

import com.ims.model.Invoice;
import com.ims.model.Payment;
import com.ims.repository.InvoiceRepository;
import com.ims.repository.PaymentRepository;
import com.ims.util.PaymentStatus;
import com.ims.util.Utils;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ims.util.Utils.getCurrentDateTime;
import static com.ims.util.Utils.getUniqueId;

@Service
public class InvoiceService {

    private final Logger log = Utils.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findAllByStatus(status);
    }

    public Invoice getInvoiceById(String id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public Invoice addInvoice(Invoice invoice) {

        invoice.setInvoiceId(getUniqueId());
        invoice.setInvoiceDate(getCurrentDateTime());
        invoice.setDueDate(invoice.getDueDate());
        invoice.setInvoiceAmount(invoice.getInvoiceAmount());
        invoice.setPaidAmount(Utils.getInitialAmount());
        invoice.setStatus(PaymentStatus.PENDING.getValue());
        invoice.setArchivedInvoiceId("");

        return invoiceRepository.save(invoice);
    }

    public void deleteAllInvoices() {
        invoiceRepository.deleteAll();
    }

    public void deleteInvoiceById(String id) {
        invoiceRepository.deleteById(id);
    }

    @Transactional
    public void updateInvoicePaymentStatus(String invoiceId) {

        List<Payment> paymentList = paymentRepository.findByInvoiceId(invoiceId).stream().toList();

        if (paymentList.isEmpty()) {
            log.info("updateInvoicePaymentStatus - No payments found, nothing to update");
        } else {

            double totalAmountPaid = paymentList.stream()
                    .mapToDouble(Payment::getPaymentAmount)
                    .sum();

            Invoice invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));

            invoice.setPaidAmount(totalAmountPaid);
            if (totalAmountPaid >= invoice.getInvoiceAmount()) {
                invoice.setStatus(PaymentStatus.PAID.getValue());
            } else {
                invoice.setStatus(PaymentStatus.PENDING.getValue());
            }
            invoiceRepository.save(invoice);

            log.debug("updateInvoicePaymentStatus - Payments found, Invoice was updated successfully : {}", ResponseEntity.ok(invoice));
        }
    }

}
