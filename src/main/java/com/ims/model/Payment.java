package com.ims.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @PrimaryKey
    @JsonProperty("paymentId")
    private String paymentId;
    @JsonProperty("id")
    private String invoiceId;
    @JsonProperty("payment_amount")
    private double paymentAmount;
    @JsonProperty("paid_date")
    private String paymentDate;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("payment_status")
    private String paymentStatus;
}
