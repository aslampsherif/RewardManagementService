package com.myStore.rewardManagement.service;

import com.myStore.rewardManagement.dto.RewardsResponse;
import com.myStore.rewardManagement.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RewardsServiceTest {

    RewardsService rewardsService = new RewardsService();

    @Test
    @DisplayName("Test getRewards when both id and list of month are not provided")
    void testGetRewards_success1() {

        List<RewardsResponse> rewardsResponseList = rewardsService.getRewards(null, 0);
        assertEquals(3, rewardsResponseList.size());
        assertEquals(1, rewardsResponseList.getFirst().getCustomerDetails().getCustomerId());
        assertEquals("Aslam", rewardsResponseList.getFirst().getCustomerDetails().getName());
        assertEquals(9876543210L, rewardsResponseList.getFirst().getCustomerDetails().getPhoneNumber());
        assertEquals("601 Corner Meadows Way", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getZip());

        List<Month> months = List.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH);
        List<Double> rewards = List.of(25.0, 140.0, 35.0);

        assertTrue(months.contains(rewardsResponseList.getFirst().getMonthlyRewardPoints().getFirst().getMonth()));
        assertTrue(rewards.contains(rewardsResponseList.getFirst().getMonthlyRewardPoints().getFirst().getRewardPoints()));
        assertEquals(200, rewardsResponseList.getFirst().getTotalRewardPoints());

    }

    @Test
    @DisplayName("Test getRewards when id is provided and list of month is not provided")
    void testGetRewards_success2() {
        List<RewardsResponse> rewardsResponseList = rewardsService.getRewards(null, 1);
        assertEquals(1, rewardsResponseList.size());
        assertEquals(1, rewardsResponseList.getFirst().getCustomerDetails().getCustomerId());
        assertEquals("Aslam", rewardsResponseList.getFirst().getCustomerDetails().getName());
        assertEquals(9876543210L, rewardsResponseList.getFirst().getCustomerDetails().getPhoneNumber());
        assertEquals("601 Corner Meadows Way", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getZip());

        List<Month> months = List.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH);
        List<Double> rewards = List.of(25.0, 140.0, 35.0);

        assertTrue(months.contains(rewardsResponseList.getFirst().getMonthlyRewardPoints().getFirst().getMonth()));
        assertTrue(rewards.contains(rewardsResponseList.getFirst().getMonthlyRewardPoints().getFirst().getRewardPoints()));
        assertEquals(200, rewardsResponseList.getFirst().getTotalRewardPoints());
    }

    @Test
    @DisplayName("Test getRewards when id is not provided and list of month is provided")
    void testGetRewards_success3() {

        List<RewardsResponse> rewardsResponseList = rewardsService.getRewards(List.of(Month.JANUARY, Month.FEBRUARY), 0);
        assertEquals(3, rewardsResponseList.size());
        assertEquals(3, rewardsResponseList.getLast().getCustomerDetails().getCustomerId());
        assertEquals("Teja", rewardsResponseList.getLast().getCustomerDetails().getName());
        assertEquals(9876543212L, rewardsResponseList.getLast().getCustomerDetails().getPhoneNumber());
        assertEquals("603 Corner Meadows Way", rewardsResponseList.getLast().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getLast().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getLast().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getLast().getCustomerDetails().getAddress().getZip());

        List<Month> months = List.of(Month.JANUARY, Month.FEBRUARY);
        List<Double> rewards = List.of(0.0, 189.0);

        assertEquals(2, rewardsResponseList.getLast().getMonthlyRewardPoints().size());
        assertTrue(months.contains(rewardsResponseList.getLast().getMonthlyRewardPoints().getFirst().getMonth()));
        assertTrue(rewards.contains(rewardsResponseList.getLast().getMonthlyRewardPoints().getFirst().getRewardPoints()));
        assertEquals(189, rewardsResponseList.getLast().getTotalRewardPoints());
    }

    @Test
    @DisplayName("Test getRewards when both id and list of month are provided")
    void testGetRewards_success4() {
        List<RewardsResponse> rewardsResponseList = rewardsService.getRewards(List.of(Month.JANUARY), 3);
        assertEquals(1, rewardsResponseList.size());
        assertEquals(3, rewardsResponseList.getLast().getCustomerDetails().getCustomerId());
        assertEquals("Teja", rewardsResponseList.getLast().getCustomerDetails().getName());
        assertEquals(9876543212L, rewardsResponseList.getLast().getCustomerDetails().getPhoneNumber());
        assertEquals("603 Corner Meadows Way", rewardsResponseList.getLast().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getLast().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getLast().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getLast().getCustomerDetails().getAddress().getZip());

        assertEquals(1, rewardsResponseList.getLast().getMonthlyRewardPoints().size());
        assertEquals(Month.JANUARY, rewardsResponseList.getLast().getMonthlyRewardPoints().getFirst().getMonth());
        assertEquals(0, rewardsResponseList.getLast().getMonthlyRewardPoints().getFirst().getRewardPoints());
        assertEquals(0, rewardsResponseList.getLast().getTotalRewardPoints());
    }

    @Test
    @DisplayName("Test getRewards when customer details not found")
    void testGetRewards_failure1() {

        ServiceException serviceException = assertThrows(ServiceException.class,
                () -> rewardsService.getRewards(null, 10));

        assertEquals("Customer details are not found", serviceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, serviceException.getHttpStatus());
    }

    @Test
    @DisplayName("Test getRewards when transaction details not found")
    void testGetRewards_failure2() {

        ServiceException serviceException = assertThrows(ServiceException.class,
                () -> rewardsService.getRewards(null, 4));

        assertEquals("Transaction details are not found", serviceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, serviceException.getHttpStatus());
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when id is provided")
    void testGetRewardsForPeriod_success1() {

        List<RewardsResponse> rewardsResponseList = rewardsService.getRewardsForPeriod(1, Month.JANUARY, Month.MARCH);
        assertEquals(1, rewardsResponseList.size());
        assertEquals(1, rewardsResponseList.getFirst().getCustomerDetails().getCustomerId());
        assertEquals("Aslam", rewardsResponseList.getFirst().getCustomerDetails().getName());
        assertEquals(9876543210L, rewardsResponseList.getFirst().getCustomerDetails().getPhoneNumber());
        assertEquals("601 Corner Meadows Way", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getFirst().getCustomerDetails().getAddress().getZip());

        List<Month> months = List.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH);
        List<Double> rewards = List.of(25.0, 140.0, 35.0);

        assertEquals(3, rewardsResponseList.getFirst().getMonthlyRewardPoints().size());
        assertTrue(months.contains(rewardsResponseList.getFirst().getMonthlyRewardPoints().getFirst().getMonth()));
        assertTrue(rewards.contains(rewardsResponseList.getFirst().getMonthlyRewardPoints().getFirst().getRewardPoints()));
        assertEquals(200, rewardsResponseList.getFirst().getTotalRewardPoints());
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when id is not provided")
    void testGetRewardsForPeriod_success2() {
        List<RewardsResponse> rewardsResponseList = rewardsService.getRewardsForPeriod(0, Month.FEBRUARY, Month.MARCH);
        assertEquals(3, rewardsResponseList.size());
        assertEquals(3, rewardsResponseList.getLast().getCustomerDetails().getCustomerId());
        assertEquals("Teja", rewardsResponseList.getLast().getCustomerDetails().getName());
        assertEquals(9876543212L, rewardsResponseList.getLast().getCustomerDetails().getPhoneNumber());
        assertEquals("603 Corner Meadows Way", rewardsResponseList.getLast().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getLast().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getLast().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getLast().getCustomerDetails().getAddress().getZip());

        List<Month> months = List.of(Month.FEBRUARY, Month.MARCH);
        List<Double> rewards = List.of(189.0, 290.0);

        assertEquals(2, rewardsResponseList.getLast().getMonthlyRewardPoints().size());
        assertTrue(months.contains(rewardsResponseList.getLast().getMonthlyRewardPoints().getFirst().getMonth()));
        assertTrue(rewards.contains(rewardsResponseList.getLast().getMonthlyRewardPoints().getFirst().getRewardPoints()));
        assertEquals(479, rewardsResponseList.getLast().getTotalRewardPoints());
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when customer details not found")
    void testGetRewardsForPeriod_failure1() {

        ServiceException serviceException = assertThrows(ServiceException.class,
                () -> rewardsService.getRewardsForPeriod(10, Month.JANUARY, Month.FEBRUARY));

        assertEquals("Customer details are not found", serviceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, serviceException.getHttpStatus());
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when transaction details not found")
    void testGetRewardsForPeriod_failure2() {

        ServiceException serviceException = assertThrows(ServiceException.class,
                () -> rewardsService.getRewardsForPeriod(4, Month.JANUARY, Month.FEBRUARY));

        assertEquals("Transaction details are not found", serviceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, serviceException.getHttpStatus());
    }
}