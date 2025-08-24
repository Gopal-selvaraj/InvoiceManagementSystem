package com.ims.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentTest {

    @Test
    void shouldCreatePaymentWithAllFields() {
        Payment payment = new Payment("1", "INV123", 100.0, "2023-10-01", "CREDIT_CARD", "COMPLETED");
        validatePayment(payment, "1", "INV123", 100.0, "2023-10-01", "CREDIT_CARD", "COMPLETED");
    }

    @Test
    void shouldAllowUpdatingPaymentFields() {
        Payment payment = new Payment();
        payment.setPaymentId("2");
        payment.setInvoiceId("INV456");
        payment.setPaymentAmount(200.0);
        payment.setPaymentDate("2023-10-02");
        payment.setPaymentMethod("DEBIT_CARD");
        payment.setPaymentStatus("PENDING");

        validatePayment(payment, "2", "INV456", 200.0, "2023-10-02", "DEBIT_CARD", "PENDING");
    }

    private void validatePayment(Payment payment, String expectedId, String expectedInvoiceId, double expectedAmount,
                                 String expectedDate, String expectedMethod, String expectedStatus) {
        assertEquals(expectedId, payment.getPaymentId());
        assertEquals(expectedInvoiceId, payment.getInvoiceId());
        assertEquals(expectedAmount, payment.getPaymentAmount());
        assertEquals(expectedDate, payment.getPaymentDate());
        assertEquals(expectedMethod, payment.getPaymentMethod());
        assertEquals(expectedStatus, payment.getPaymentStatus());
    }

    @Test
    void shouldHandleNegativePaymentAmount() {
        Payment payment = new Payment("3", "INV789", -50.0, "2023-10-03", "CASH", "FAILED");
        assertEquals(-50.0, payment.getPaymentAmount());
    }

    @Test
    void shouldHandleNullPaymentDate() {
        Payment payment = new Payment("4", "INV101", 150.0, null, "BANK_TRANSFER", "COMPLETED");
        assertNull(payment.getPaymentDate());
    }

    @Test
    void shouldHandleEmptyPaymentMethod() {
        Payment payment = new Payment("5", "INV202", 75.0, "2023-10-04", "", "PENDING");
        assertEquals("", payment.getPaymentMethod());
    }

    @Test
    void shouldHandleNullPaymentStatus() {
        Payment payment = new Payment("6", "INV303", 120.0, "2023-10-05", "PAYPAL", null);
        assertNull(payment.getPaymentStatus());
    }

}
