package com.myStore.rewardManagement.service;

import com.myStore.rewardManagement.dto.RewardsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertEquals(25, rewardsResponseList.getFirst().getRewardPoints().get("JANUARY"));
        assertEquals(140, rewardsResponseList.getFirst().getRewardPoints().get("FEBRUARY"));
        assertEquals(35, rewardsResponseList.getFirst().getRewardPoints().get("MARCH"));
        assertEquals(200, rewardsResponseList.getFirst().getRewardPoints().get("totalRewardPoints"));

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
        assertEquals(25, rewardsResponseList.getFirst().getRewardPoints().get("JANUARY"));
        assertEquals(140, rewardsResponseList.getFirst().getRewardPoints().get("FEBRUARY"));
        assertEquals(35, rewardsResponseList.getFirst().getRewardPoints().get("MARCH"));
        assertEquals(200, rewardsResponseList.getFirst().getRewardPoints().get("totalRewardPoints"));
    }

    @Test
    @DisplayName("Test getRewards when id is not provided and list of month is provided")
    void testGetRewards_success3() {

        List<RewardsResponse> rewardsResponseList = rewardsService.getRewards(List.of(Month.JANUARY, Month.FEBRUARY, Month.APRIL), 0);
        assertEquals(3, rewardsResponseList.size());
        assertEquals(3, rewardsResponseList.getLast().getCustomerDetails().getCustomerId());
        assertEquals("Teja", rewardsResponseList.getLast().getCustomerDetails().getName());
        assertEquals(9876543212L, rewardsResponseList.getLast().getCustomerDetails().getPhoneNumber());
        assertEquals("603 Corner Meadows Way", rewardsResponseList.getLast().getCustomerDetails().getAddress().getStreet());
        assertEquals("Calgary", rewardsResponseList.getLast().getCustomerDetails().getAddress().getCity());
        assertEquals("Alberta", rewardsResponseList.getLast().getCustomerDetails().getAddress().getProvince());
        assertEquals("T3N 2C5", rewardsResponseList.getLast().getCustomerDetails().getAddress().getZip());
        assertEquals(0, rewardsResponseList.getLast().getRewardPoints().get("JANUARY"));
        assertEquals(189, rewardsResponseList.getLast().getRewardPoints().get("FEBRUARY"));
        assertNull(rewardsResponseList.getLast().getRewardPoints().get("MARCH"));
        assertNull(rewardsResponseList.getLast().getRewardPoints().get("APRIL"));
        assertEquals(189, rewardsResponseList.getLast().getRewardPoints().get("totalRewardPoints"));
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
        assertEquals(0, rewardsResponseList.getLast().getRewardPoints().get("JANUARY"));
        assertNull(rewardsResponseList.getLast().getRewardPoints().get("FEBRUARY"));
        assertNull(rewardsResponseList.getLast().getRewardPoints().get("MARCH"));
        assertEquals(0, rewardsResponseList.getLast().getRewardPoints().get("totalRewardPoints"));
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
        assertEquals(25, rewardsResponseList.getFirst().getRewardPoints().get("JANUARY"));
        assertEquals(140, rewardsResponseList.getFirst().getRewardPoints().get("FEBRUARY"));
        assertEquals(35, rewardsResponseList.getFirst().getRewardPoints().get("MARCH"));
        assertEquals(200, rewardsResponseList.getFirst().getRewardPoints().get("totalRewardPoints"));
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
        assertNull(rewardsResponseList.getLast().getRewardPoints().get("JANUARY"));
        assertEquals(189, rewardsResponseList.getLast().getRewardPoints().get("FEBRUARY"));
        assertEquals(290, rewardsResponseList.getLast().getRewardPoints().get("MARCH"));
        assertEquals(479, rewardsResponseList.getLast().getRewardPoints().get("totalRewardPoints"));
    }
}
