package com.ims.util;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAID("Paid"),
    PENDING("Pending"),
    VOID("Void");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

}
