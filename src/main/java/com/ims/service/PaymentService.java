package com.ims.service;

import com.ims.model.Payment;
import com.ims.repository.PaymentRepository;
import com.ims.util.PaymentMethod;
import com.ims.util.PaymentStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import static com.ims.util.Utils.getCurrentDateTime;
import static com.ims.util.Utils.getUniqueId;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    InvoiceService invoiceService;

    public List<Payment> getPaymentsByInvoiceId(String id) {
        return paymentRepository.findByInvoiceId(id);
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found for id: " + id));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional
    public Payment addPayment(String invoiceId, Payment payment) {
        Payment paymentInfo = new Payment();

        paymentInfo.setPaymentId(getUniqueId());
        paymentInfo.setPaymentDate(getCurrentDateTime());
        paymentInfo.setInvoiceId(invoiceId);
        paymentInfo.setPaymentAmount(payment.getPaymentAmount());
        paymentInfo.setPaymentMethod(PaymentMethod.valueOf(payment.getPaymentMethod().toUpperCase()).getValue());
        paymentInfo.setPaymentStatus(PaymentStatus.PAID.getValue());

        paymentRepository.save(paymentInfo);

        invoiceService.updateInvoicePaymentStatus(invoiceId);

        return paymentInfo;

    }

    public void deletePaymentsByInvoiceId(String id) {
        paymentRepository.deleteByInvoiceId(id);
    }

    public void deletePaymentById(String id) {
        paymentRepository.deleteById(id);
    }

    public void deleteAllPayments() {
        paymentRepository.deleteAll();
    }
}
