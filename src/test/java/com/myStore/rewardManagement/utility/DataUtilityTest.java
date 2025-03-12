package com.myStore.rewardManagement.utility;

import com.myStore.rewardManagement.dto.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataUtilityTest {

    @Test
    @DisplayName("Test getAllTransactions when only customer id is provided")
    void testGetAllTransactions_success1() {

        List<Transaction> transactions = DataUtility.getAllTransactions(1, null);

        assertEquals(9, transactions.size());
        assertEquals(1, transactions.getFirst().getCustomerId());
        assertEquals(20, transactions.getFirst().getTransactionAmount());
        assertEquals(LocalDateTime.parse("2025-01-10T10:00:00.00"), transactions.getFirst().getTransactionTime());
    }

    @Test
    @DisplayName("Test getAllTransactions when both customer id and month list are not provided")
    void testGetAllTransactions_success2() {

        List<Transaction> transactions = DataUtility.getAllTransactions(0, null);

        assertEquals(27, transactions.size());
        assertEquals(3, transactions.getLast().getCustomerId());
        assertEquals(20, transactions.getLast().getTransactionAmount());
        assertEquals(LocalDateTime.parse("2025-03-18T10:00:00.00"), transactions.getLast().getTransactionTime());
    }

    @Test
    @DisplayName("Test getAllTransactions when both customer id and month list are provided")
    void testGetAllTransactions_success4() {

        List<Transaction> transactions = DataUtility.getAllTransactions(1, List.of(Month.JANUARY, Month.FEBRUARY));

        assertEquals(6, transactions.size());
        assertEquals(1, transactions.getFirst().getCustomerId());
        assertEquals(20, transactions.getFirst().getTransactionAmount());
        assertEquals(LocalDateTime.parse("2025-01-10T10:00:00.00"), transactions.getFirst().getTransactionTime());
    }

    @Test
    @DisplayName("Test getAllTransactions when only month list is provided")
    void testGetAllTransactions_success3() {

        List<Transaction> transactions = DataUtility.getAllTransactions(0, List.of(Month.JANUARY, Month.FEBRUARY));

        assertEquals(18, transactions.size());
        assertEquals(1, transactions.getFirst().getCustomerId());
        assertEquals(20, transactions.getFirst().getTransactionAmount());
        assertEquals(LocalDateTime.parse("2025-01-10T10:00:00.00"), transactions.getFirst().getTransactionTime());
    }

    @Test
    @DisplayName("Test getAllTransactionsForPeriod when customer id is provided")
    void testGetAllTransactionsForPeriod_success1() {

        List<Transaction> transactions = DataUtility.getAllTransactionsForPeriod(1, Month.FEBRUARY, Month.MARCH);

        assertEquals(6, transactions.size());
        assertEquals(1, transactions.getFirst().getCustomerId());
        assertEquals(100, transactions.getFirst().getTransactionAmount());
        assertEquals(LocalDateTime.parse("2025-02-10T10:00:00.00"), transactions.getFirst().getTransactionTime());
    }

    @Test
    @DisplayName("Test getAllTransactionsForPeriod when customer id is not provided")
    void testGetAllTransactionsForPeriod_success2() {

        List<Transaction> transactions = DataUtility.getAllTransactionsForPeriod(0, Month.FEBRUARY, Month.MARCH);

        assertEquals(18, transactions.size());
        assertEquals(1, transactions.getFirst().getCustomerId());
        assertEquals(100, transactions.getFirst().getTransactionAmount());
        assertEquals(LocalDateTime.parse("2025-02-10T10:00:00.00"), transactions.getFirst().getTransactionTime());
    }
}