package com.myStore.rewardManagement.service;

import com.myStore.rewardManagement.dto.Customer;
import com.myStore.rewardManagement.dto.RewardsResponse;
import com.myStore.rewardManagement.dto.Transaction;
import com.myStore.rewardManagement.utility.MockDataUtility;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        List<Transaction> transactionsList = MockDataUtility.getAllTransactions(customerId);
        Map<Integer, Customer> customersList = MockDataUtility.getCustomerDetails(customerId);

        List<RewardsResponse> rewardsResponses = new ArrayList<>();

        transactionsList
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId))
                .forEach((id, transactions) -> {
                    Map<String, Double> monthlyRewards = new HashMap<>();
                    transactions.forEach(transaction -> {
                        Month transactionMonth = transaction.getTransactionTime().getMonth();
                        double rewardForTransaction = findRewardForTransaction(transaction.getTransactionAmount());
                        if (CollectionUtils.isEmpty(months)) {
                            monthlyRewards.put(transactionMonth.name(), monthlyRewards.getOrDefault(transactionMonth.name(), 0.0) + rewardForTransaction);
                            monthlyRewards.put(totalRewards, monthlyRewards.getOrDefault(totalRewards, 0.0) + rewardForTransaction);
                        } else if (months.contains(transactionMonth)) {
                            monthlyRewards.put(transactionMonth.name(), monthlyRewards.getOrDefault(transactionMonth.name(), 0.0) + rewardForTransaction);
                            monthlyRewards.put(totalRewards, monthlyRewards.getOrDefault(totalRewards, 0.0) + rewardForTransaction);
                        }
                    });
                    rewardsResponses.add(RewardsResponse.builder()
                            .customerDetails(customersList.get(id))
                            .rewardPoints(monthlyRewards)
                            .build());
                });
        return rewardsResponses;
    }

    /**
     * @param customerId: customer id (optional)
     * @param startMonth: start month of time period (required)
     * @param endMonth:   end month of time period (required)
     * @return List of customer and rewards details based on customer id for a given time period.
     * Method will return details of all customers and reward details if id is not provided
     */
    public List<RewardsResponse> getRewardsForPeriod(int customerId, Month startMonth, Month endMonth) {
        List<Transaction> transactionsList = MockDataUtility.getAllTransactionsForPeriod(customerId, startMonth, endMonth);
        Map<Integer, Customer> customersList = MockDataUtility.getCustomerDetails(customerId);
        List<RewardsResponse> rewardsResponses = new ArrayList<>();

        transactionsList
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId))
                .forEach((id, transactions) -> {
                    Map<String, Double> monthlyRewards = new HashMap<>();
                    transactions.forEach(transaction -> {
                        Month transactionMonth = transaction.getTransactionTime().getMonth();
                        double rewardForTransaction = findRewardForTransaction(transaction.getTransactionAmount());
                        monthlyRewards.put(transactionMonth.name(), monthlyRewards.getOrDefault(transactionMonth.name(), 0.0) + rewardForTransaction);
                        monthlyRewards.put(totalRewards, monthlyRewards.getOrDefault(totalRewards, 0.0) + rewardForTransaction);
                    });
                    rewardsResponses.add(RewardsResponse.builder()
                            .customerDetails(customersList.get(id))
                            .rewardPoints(monthlyRewards)
                            .build());
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
}