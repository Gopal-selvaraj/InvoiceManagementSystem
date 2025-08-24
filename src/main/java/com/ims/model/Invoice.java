package com.ims.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @PrimaryKey
    @JsonProperty("id")
    private String invoiceId;
    @JsonProperty("invoice_date")
    private String invoiceDate;
    @JsonProperty("due_date")
    private String dueDate;
    @JsonProperty("amount")
    private double invoiceAmount;
    @JsonProperty("paid_amount")
    private double paidAmount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("archived_invoiceId")
    private String archivedInvoiceId;
}