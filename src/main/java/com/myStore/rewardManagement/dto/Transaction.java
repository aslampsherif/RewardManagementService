package com.myStore.rewardManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private int customerId;
    private double transactionAmount;
    private LocalDateTime transactionTime;
}