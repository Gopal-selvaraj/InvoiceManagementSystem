package com.ims.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OverdueTest {

    @Test
    void shouldCreateOverdueWithAllFields() {
        Overdue overdue = new Overdue("1", "2023-10-01", 5, 50.0);
        validateOverdue(overdue, "1", "2023-10-01", 5, 50.0);
    }

    @Test
    void shouldAllowUpdatingOverdueFields() {
        Overdue overdue = new Overdue();
        overdue.setOverdueId("2");
        overdue.setOverdueDate("2023-10-02");
        overdue.setOverdueDays(10);
        overdue.setLateFee(100.0);

        validateOverdue(overdue, "2", "2023-10-02", 10, 100.0);
    }

    private void validateOverdue(Overdue overdue, String expectedId, String expectedDate, int expectedDays, double expectedFee) {
        assertEquals(expectedId, overdue.getOverdueId());
        assertEquals(expectedDate, overdue.getOverdueDate());
        assertEquals(expectedDays, overdue.getOverdueDays());
        assertEquals(expectedFee, overdue.getLateFee());
    }

    @Test
    void shouldHandleNegativeOverdueDays() {
        Overdue overdue = new Overdue("3", "2023-10-03", -1, 0.0);
        assertEquals(-1, overdue.getOverdueDays());
    }

    @Test
    void shouldHandleZeroLateFee() {
        Overdue overdue = new Overdue("4", "2023-10-04", 0, 0.0);
        assertEquals(0.0, overdue.getLateFee());
    }

    @Test
    void shouldHandleEmptyOverdueId() {
        Overdue overdue = new Overdue("", "2023-10-05", 3, 30.0);
        assertEquals("", overdue.getOverdueId());
    }

    @Test
    void shouldHandleNullOverdueDate() {
        Overdue overdue = new Overdue("5", null, 7, 70.0);
        assertNull(overdue.getOverdueDate());
    }
}
