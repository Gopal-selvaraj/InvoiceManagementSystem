package com.ims.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("overdue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Overdue {
    @PrimaryKey
    @JsonProperty("overdueId")
    private String overdueId;
    @JsonProperty("overdue_date")
    private String overdueDate;
    @JsonProperty("overdue_days")
    private int overdueDays;
    @JsonProperty("late_fee")
    private double lateFee;
}
