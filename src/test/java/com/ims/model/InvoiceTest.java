package com.ims.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


class InvoiceTest {

    @Test
    void shouldCreateInvoiceWithAllFields() {
        Invoice invoice = new Invoice("1", "2023-10-01", "2023-10-15", 200.0, 50.0, "PENDING", "ARCH123");
        validateInvoice(invoice, "1", "2023-10-01", "2023-10-15", 200.0, 50.0, "PENDING", "ARCH123");
    }

    @Test
    void shouldAllowUpdatingInvoiceFields() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId("2");
        invoice.setInvoiceDate("2023-10-02");
        invoice.setDueDate("2023-10-16");
        invoice.setInvoiceAmount(300.0);
        invoice.setPaidAmount(100.0);
        invoice.setStatus("PAID");
        invoice.setArchivedInvoiceId("ARCH456");

        validateInvoice(invoice, "2", "2023-10-02", "2023-10-16", 300.0, 100.0, "PAID", "ARCH456");
    }

    private void validateInvoice(Invoice invoice, String expectedId, String expectedDate, String expectedDueDate,
                                 double expectedAmount, double expectedPaidAmount, String expectedStatus,
                                 String expectedArchivedId) {
        assertEquals(expectedId, invoice.getInvoiceId());
        assertEquals(expectedDate, invoice.getInvoiceDate());
        assertEquals(expectedDueDate, invoice.getDueDate());
        assertEquals(expectedAmount, invoice.getInvoiceAmount());
        assertEquals(expectedPaidAmount, invoice.getPaidAmount());
        assertEquals(expectedStatus, invoice.getStatus());
        assertEquals(expectedArchivedId, invoice.getArchivedInvoiceId());
    }

    @Test
    void shouldHandleNegativeInvoiceAmount() {
        Invoice invoice = new Invoice("3", "2023-10-03", "2023-10-17", -100.0, 0.0, "PENDING", null);
        assertEquals(-100.0, invoice.getInvoiceAmount());
    }

    @Test
    void shouldHandleNullArchivedInvoiceId() {
        Invoice invoice = new Invoice("4", "2023-10-04", "2023-10-18", 150.0, 50.0, "PENDING", null);
        assertNull(invoice.getArchivedInvoiceId());
    }

    @Test
    void shouldHandleEmptyStatus() {
        Invoice invoice = new Invoice("5", "2023-10-05", "2023-10-19", 250.0, 0.0, "", "ARCH789");
        assertEquals("", invoice.getStatus());
    }

    @Test
    void shouldHandleNullInvoiceDate() {
        Invoice invoice = new Invoice("6", null, "2023-10-20", 100.0, 0.0, "PENDING", "ARCH101");
        assertNull(invoice.getInvoiceDate());
    }
}
