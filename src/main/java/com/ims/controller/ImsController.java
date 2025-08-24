package com.ims.controller;


import com.ims.model.Invoice;
import com.ims.model.Overdue;
import com.ims.model.Payment;
import com.ims.service.InvoiceService;
import com.ims.service.OverdueService;
import com.ims.service.PaymentService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/ims")
public class ImsController {

    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final OverdueService overdueService;

    public ImsController(InvoiceService invoiceService, PaymentService paymentService, OverdueService overdueService) {
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.overdueService = overdueService;
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/invoices/status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status));
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceService.addInvoice(invoice));
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoiceById(@PathVariable String id) {
        invoiceService.deleteInvoiceById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invoices")
    public ResponseEntity<Void> deleteAllInvoices() {
        invoiceService.deleteAllInvoices();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invoices/{id}/payments")
    public ResponseEntity<List<Payment>> getPaymentsByInvoiceId(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentsByInvoiceId(id));
    }

    @GetMapping("/invoices/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/invoices/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping("/invoices/{id}/payments")
    public ResponseEntity<Payment> addPayment(@PathVariable String id, @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.addPayment(id, payment));
    }

    @DeleteMapping("/invoices/{id}/payments")
    public ResponseEntity<Void> deletePaymentsByInvoiceId(@PathVariable String id) {
        paymentService.deletePaymentsByInvoiceId(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invoices/payments/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable String id) {
        paymentService.deletePaymentById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invoices/payments")
    public ResponseEntity<Void> deleteAllPayments() {
        paymentService.deleteAllPayments();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invoices/overdue/{id}")
    public ResponseEntity<Overdue> getOverdueById(@PathVariable String id) {
        return ResponseEntity.ok(overdueService.getOverdueById(id));
    }

    @GetMapping("/invoices/overdue")
    public ResponseEntity<List<Overdue>> getAllOverdue() {
        return ResponseEntity.ok(overdueService.getAllOverdue());
    }

    @PostMapping("/invoices/process-overdue")
    public ResponseEntity<Overdue> processOverdue(@RequestBody Overdue overdue) {
        return ResponseEntity.ok(overdueService.processOverdue(overdue));
    }

    @DeleteMapping("/invoices/overdue/{id}")
    public ResponseEntity<Void> deleteOverdueById(@PathVariable String id) {
        overdueService.deleteOverdueById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invoices/overdue")
    public ResponseEntity<Void> deleteAllOverdue() {
        overdueService.deleteAllOverdue();
        return ResponseEntity.ok().build();
    }
}
