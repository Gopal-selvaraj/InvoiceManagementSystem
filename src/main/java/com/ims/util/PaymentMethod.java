package com.ims.util;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("Cash"),
    BANK_TRANSFER("Bank Transfer"),
    THIRD_PARTY_PAYMENT("Third Party Payment"),
    CARD("Card");

    private final String value;

    PaymentMethod(String value) {
        this.value = value != null ? value : this.name();
    }

}
