package com.myStore.rewardManagement.service;

import com.myStore.rewardManagement.dto.Customer;
import com.myStore.rewardManagement.dto.MonthlyReward;
import com.myStore.rewardManagement.dto.RewardsResponse;
import com.myStore.rewardManagement.dto.Transaction;
import com.myStore.rewardManagement.exception.ServiceException;
import com.myStore.rewardManagement.utility.DataUtility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsService {

    private final String totalRewards = "totalRewardPoints";

    /**
     * @param months:     list of months (optional)
     * @param customerId: customer id (optional)
     * @return List of customer and reward details based on month and customer id.
     * Method will return details of all customers and rewards details if id and month is not provided.
     */
    public List<RewardsResponse> getRewards(List<Month> months, int customerId) {

        Map<Integer, Customer> customersList = DataUtility.getCustomerDetails(customerId);
        if (customersList.isEmpty()) {
            throw new ServiceException("Customer details are not found", HttpStatus.NOT_FOUND);
        }

        List<Transaction> transactionsList = DataUtility.getAllTransactions(customerId, months);
        if (transactionsList.isEmpty()) {
            throw new ServiceException("Transaction details are not found", HttpStatus.NOT_FOUND);
        }

        return populateRewardResponseList(transactionsList, customersList);
    }

    /**
     * @param customerId: customer id (optional)
     * @param startMonth: start month of time period (required)
     * @param endMonth:   end month of time period (required)
     * @return List of customer and rewards details based on customer id for a given time period.
     * Method will return details of all customers and reward details if id is not provided
     */
    public List<RewardsResponse> getRewardsForPeriod(int customerId, Month startMonth, Month endMonth) {

        Map<Integer, Customer> customersList = DataUtility.getCustomerDetails(customerId);
        if (customersList.isEmpty()) {
            throw new ServiceException("Customer details are not found", HttpStatus.NOT_FOUND);
        }

        List<Transaction> transactionsList = DataUtility.getAllTransactionsForPeriod(customerId, startMonth, endMonth);
        if (transactionsList.isEmpty()) {
            throw new ServiceException("Transaction details are not found", HttpStatus.NOT_FOUND);
        }

        return populateRewardResponseList(transactionsList, customersList);
    }

    /**
     * @param transactionsList list of transactions
     * @param customersList    map with key as customer id and value as customer details
     * @return final list of reward responses
     */
    private List<RewardsResponse> populateRewardResponseList(List<Transaction> transactionsList, Map<Integer, Customer> customersList) {
        List<RewardsResponse> rewardsResponses = new ArrayList<>();
        transactionsList
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId))
                .forEach((id, transactions) -> {
                    Map<Month, Double> monthlyRewards = new HashMap<>();
                    transactions.forEach(transaction -> {
                        Month transactionMonth = transaction.getTransactionTime().getMonth();
                        double rewardForTransaction = findRewardForTransaction(transaction.getTransactionAmount());
                        monthlyRewards.put(transactionMonth, monthlyRewards.getOrDefault(transactionMonth, 0.0) + rewardForTransaction);
                    });
                    addRewardResponsesToList(rewardsResponses, customersList.get(id), monthlyRewards);
                });

        return rewardsResponses;
    }

    /**
     * @param transactionAmount: transacted amount
     * @return calculated reward point
     */
    private double findRewardForTransaction(double transactionAmount) {
        if (transactionAmount > 50 && transactionAmount <= 100) {
            return transactionAmount - 50;
        } else if (transactionAmount > 100) {
            return 50 + (transactionAmount - 100) * 2;
        }
        return 0;
    }

    /**
     * @param rewardsResponseList: List to which responses have to be added
     * @param customer:            Customer details
     * @param monthlyRewards:      map with key as month and value as reward points for that month
     */
    private void addRewardResponsesToList(List<RewardsResponse> rewardsResponseList, Customer customer, Map<Month, Double> monthlyRewards) {

        List<MonthlyReward> monthlyRewardsList = new ArrayList<>();
        monthlyRewards.forEach((month, rewards) -> {
            monthlyRewardsList.add(MonthlyReward.builder()
                    .month(month)
                    .rewardPoints(rewards)
                    .build());
        });
        double totalMonthlyRewards = monthlyRewardsList.stream()
                .mapToDouble(MonthlyReward::getRewardPoints)
                .sum();

        rewardsResponseList.add(RewardsResponse.builder()
                .customerDetails(customer)
                .monthlyRewardPoints(monthlyRewardsList)
                .totalRewardPoints(totalMonthlyRewards)
                .build());
    }
}