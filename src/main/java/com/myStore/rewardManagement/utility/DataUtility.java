package com.myStore.rewardManagement.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myStore.rewardManagement.dto.Customer;
import com.myStore.rewardManagement.dto.Transaction;
import com.myStore.rewardManagement.exception.ServiceException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class used to get customer and transaction details.
 * Customer details and transaction details are mocked and placed in resources/data directory
 */

public class DataUtility {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * @param customerId = customer ID (optional)
     * @return List of transactions
     * Method will return list of transactions of all customers if customer id is not provided. Otherwise, it will return
     * all transactions of individual customer
     */
    public static List<Transaction> getAllTransactions(int customerId) {
        try {
            InputStream transactionsInputStream = new ClassPathResource("data/Transactions.json").getInputStream();
            List<Transaction> transactionsList = mapper.readValue(transactionsInputStream, mapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));
            if (customerId == 0) {
                return transactionsList;
            } else {
                return transactionsList.stream()
                        .filter(transaction -> transaction.getCustomerId() == customerId)
                        .toList();
            }
        } catch (IOException e) {
            throw new ServiceException("Exception occurred while reading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param customerId: Customer id
     * @param startMonth: Start month of time frame (inclusive)
     * @param endMonth:   End month of time frame (inclusive)
     * @return List of transaction between a period (inclusive)
     * It returns transaction details of customer if customer id is provided. Otherwise, returns transactions of all customers
     * it filters data with start month and end month of period
     */
    public static List<Transaction> getAllTransactionsForPeriod(int customerId, Month startMonth, Month endMonth) {
        try {
            InputStream transactionsInputStream = new ClassPathResource("data/Transactions.json").getInputStream();
            List<Transaction> transactionsList = mapper.readValue(transactionsInputStream, mapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));
            List<Transaction> filteredList = transactionsList.stream()
                    .filter(transaction -> (transaction.getTransactionTime().getMonth().getValue() >= startMonth.getValue())
                            && transaction.getTransactionTime().getMonth().getValue() <= endMonth.getValue())
                    .toList();

            if (customerId == 0) {
                return filteredList;
            } else {
                return filteredList.stream()
                        .filter(transaction -> transaction.getCustomerId() == customerId)
                        .toList();
            }
        } catch (IOException e) {
            throw new ServiceException("Exception occurred while reading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param customerId = Customer ID (optional)
     * @return Map with key as customer id and value as customer details
     * Method will return details of a customer if customer id is provided. Otherwise, it will return details of all customers
     */
    public static Map<Integer, Customer> getCustomerDetails(int customerId) {
        try {
            InputStream customersInputStream = new ClassPathResource("data/Customers.json").getInputStream();
            List<Customer> customers = mapper.readValue(customersInputStream, mapper.getTypeFactory().constructCollectionType(List.class, Customer.class));

            if (customerId == 0) {
                return customers.stream()
                        .collect(Collectors.toMap(Customer::getCustomerId, customer -> customer));
            } else {
                return customers.stream()
                        .filter(customer -> customer.getCustomerId() == customerId)
                        .collect(Collectors.toMap(Customer::getCustomerId, customer -> customer));
            }
        } catch (IOException e) {
            throw new ServiceException("Exception occurred while reading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}