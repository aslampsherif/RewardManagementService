package com.myStore.rewardManagement.controller;

import com.myStore.rewardManagement.dto.Address;
import com.myStore.rewardManagement.dto.Customer;
import com.myStore.rewardManagement.dto.RewardsResponse;
import com.myStore.rewardManagement.service.RewardsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardsControllerTest {

    @Mock
    RewardsService rewardsService;

    @InjectMocks
    RewardsController rewardsController;

    @Test
    @DisplayName("Test getRewards success")
    void testGetRewards() {

        List<RewardsResponse> mockedResponse = new ArrayList<>();
        mockedResponse.add(RewardsResponse.builder()
                .customerDetails(Customer.builder()
                        .customerId(1)
                        .name("Aslam")
                        .phoneNumber(1234567890L)
                        .address(Address.builder()
                                .street("street1")
                                .city("city1")
                                .province("province1")
                                .zip("zip1")
                                .build())
                        .build())
                .rewardPoints(Map.of("JANUARY", 20.0, "totalRewardPoints", 20.0))
                .build());

        when(rewardsService.getRewards(List.of(Month.JANUARY), 1)).thenReturn(mockedResponse);

        ResponseEntity<List<RewardsResponse>> actualResponse = rewardsController.getRewards(List.of(Month.JANUARY), 1);

        assertEquals(1, Objects.requireNonNull(actualResponse.getBody()).size());
        assertEquals(1, actualResponse.getBody().getFirst().getCustomerDetails().getCustomerId());
        assertEquals("Aslam", actualResponse.getBody().getFirst().getCustomerDetails().getName());
        assertEquals(1234567890L, actualResponse.getBody().getFirst().getCustomerDetails().getPhoneNumber());
        assertEquals("street1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getStreet());
        assertEquals("city1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getCity());
        assertEquals("province1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getProvince());
        assertEquals("zip1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getZip());
        assertEquals(20, actualResponse.getBody().getFirst().getRewardPoints().get("JANUARY"));
        assertNull(actualResponse.getBody().getFirst().getRewardPoints().get("FEBRUARY"));
        assertNull(actualResponse.getBody().getFirst().getRewardPoints().get("MARCH"));
        assertEquals(20, actualResponse.getBody().getFirst().getRewardPoints().get("totalRewardPoints"));
    }

    @Test
    @DisplayName("Test getRewardsForPeriod success")
    void testGetRewardsForPeriod() {
        List<RewardsResponse> mockedResponse = new ArrayList<>();
        mockedResponse.add(RewardsResponse.builder()
                .customerDetails(Customer.builder()
                        .customerId(1)
                        .name("Aslam")
                        .phoneNumber(1234567890L)
                        .address(Address.builder()
                                .street("street1")
                                .city("city1")
                                .province("province1")
                                .zip("zip1")
                                .build())
                        .build())
                .rewardPoints(Map.of("JANUARY", 20.0, "FEBRUARY", 40.0, "totalRewardPoints", 60.0))
                .build());

        when(rewardsService.getRewardsForPeriod(1, Month.JANUARY, Month.FEBRUARY)).thenReturn(mockedResponse);

        ResponseEntity<List<RewardsResponse>> actualResponse = rewardsController.getRewardsForPeriod(Month.JANUARY, Month.FEBRUARY, 1);

        assertEquals(1, Objects.requireNonNull(actualResponse.getBody()).size());
        assertEquals(1, actualResponse.getBody().getFirst().getCustomerDetails().getCustomerId());
        assertEquals("Aslam", actualResponse.getBody().getFirst().getCustomerDetails().getName());
        assertEquals(1234567890L, actualResponse.getBody().getFirst().getCustomerDetails().getPhoneNumber());
        assertEquals("street1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getStreet());
        assertEquals("city1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getCity());
        assertEquals("province1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getProvince());
        assertEquals("zip1", actualResponse.getBody().getFirst().getCustomerDetails().getAddress().getZip());
        assertEquals(20, actualResponse.getBody().getFirst().getRewardPoints().get("JANUARY"));
        assertEquals(40, actualResponse.getBody().getFirst().getRewardPoints().get("FEBRUARY"));
        assertNull(actualResponse.getBody().getFirst().getRewardPoints().get("MARCH"));
        assertEquals(60, actualResponse.getBody().getFirst().getRewardPoints().get("totalRewardPoints"));
    }
}
