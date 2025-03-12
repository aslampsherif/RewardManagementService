package com.myStore.rewardManagement.integrationTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test getRewards when both id and list of months are not provided")
    void testGetRewards_success1() throws Exception {

        mockMvc.perform(get("/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].customerDetails.customerId").value(3))
                .andExpect(jsonPath("$[2].customerDetails.name").value("Teja"))
                .andExpect(jsonPath("$[2].customerDetails.phoneNumber").value(9876543212L))
                .andExpect(jsonPath("$[2].customerDetails.address.street").value("603 Corner Meadows Way"))
                .andExpect(jsonPath("$[2].monthlyRewardPoints").isArray())
                .andExpect(jsonPath("$[2].totalRewardPoints").value(479));
    }

    @Test
    @DisplayName("Test getRewards when both id and list of months are provided")
    void testGetRewards_success2() throws Exception {
        mockMvc.perform(get("/rewards")
                        .param("customerId", "3")
                        .param("months", "FEBRUARY,MARCH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(jsonPath("$[0].customerDetails.customerId").value(3))
                .andExpect(jsonPath("$[0].customerDetails.name").value("Teja"))
                .andExpect(jsonPath("$[0].customerDetails.phoneNumber").value(9876543212L))
                .andExpect(jsonPath("$[0].customerDetails.address.street").value("603 Corner Meadows Way"))
                .andExpect(jsonPath("$[0].monthlyRewardPoints[2])").doesNotExist())
                .andExpect(jsonPath("$[0].totalRewardPoints").value(479));
    }

    @Test
    @DisplayName("Test getRewards when id is provided and list of months are not provided")
    void testGetRewards_success3() throws Exception {
        mockMvc.perform(get("/rewards")
                        .param("customerId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(jsonPath("$[0].customerDetails.customerId").value(3))
                .andExpect(jsonPath("$[0].customerDetails.name").value("Teja"))
                .andExpect(jsonPath("$[0].customerDetails.phoneNumber").value(9876543212L))
                .andExpect(jsonPath("$[0].customerDetails.address.street").value("603 Corner Meadows Way"))
                .andExpect(jsonPath("$[0].monthlyRewardPoints").isArray())
                .andExpect(jsonPath("$[0].totalRewardPoints").value(479));
    }

    @Test
    @DisplayName("Test getRewards when id is not provided and list of months is provided")
    void testGetRewards_success4() throws Exception {
        mockMvc.perform(get("/rewards")
                        .param("months", "MARCH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].customerDetails.customerId").value(3))
                .andExpect(jsonPath("$[2].customerDetails.name").value("Teja"))
                .andExpect(jsonPath("$[2].customerDetails.phoneNumber").value(9876543212L))
                .andExpect(jsonPath("$[2].customerDetails.address.street").value("603 Corner Meadows Way"))
                .andExpect(jsonPath("$[2].monthlyRewardPoints[1]").doesNotExist())
                .andExpect(jsonPath("$[2].monthlyRewardPoints[0].month").value("MARCH"))
                .andExpect(jsonPath("$[2].monthlyRewardPoints[0].rewardPoints").value(290))
                .andExpect(jsonPath("$[2].totalRewardPoints").value(290));
    }

    @Test
    @DisplayName("Test getRewards when id is invalid")
    void testGetRewards_failure1() throws Exception {
        mockMvc.perform(get("/rewards")
                        .param("customerId", "id"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionClass").value("MethodArgumentTypeMismatchException"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Test getRewards when Month is invalid")
    void testGetRewards_failure2() throws Exception {
        mockMvc.perform(get("/rewards")
                        .param("months", "MAR"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionClass").value("MethodArgumentTypeMismatchException"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when id is not provided")
    void testGetRewardsForPeriod_success1() throws Exception {
        mockMvc.perform(get("/rewards/period")
                        .param("startMonth", "JANUARY")
                        .param("endMonth", "MARCH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].customerDetails.customerId").value(3))
                .andExpect(jsonPath("$[2].customerDetails.name").value("Teja"))
                .andExpect(jsonPath("$[2].customerDetails.phoneNumber").value(9876543212L))
                .andExpect(jsonPath("$[2].customerDetails.address.street").value("603 Corner Meadows Way"))
                .andExpect(jsonPath("$[2].monthlyRewardPoints").isArray())
                .andExpect(jsonPath("$[2].totalRewardPoints").value(479));
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when id is provided")
    void testGetRewardsForPeriod_success2() throws Exception {

        mockMvc.perform(get("/rewards/period")
                        .param("startMonth", "JANUARY")
                        .param("endMonth", "FEBRUARY")
                        .param("customerId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(jsonPath("$[0].customerDetails.customerId").value(3))
                .andExpect(jsonPath("$[0].customerDetails.name").value("Teja"))
                .andExpect(jsonPath("$[0].customerDetails.phoneNumber").value(9876543212L))
                .andExpect(jsonPath("$[0].customerDetails.address.street").value("603 Corner Meadows Way"))
                .andExpect(jsonPath("$[0].monthlyRewardPoints[2]").doesNotExist())
                .andExpect(jsonPath("$[0].totalRewardPoints").value(189));
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when id is invalid")
    void testGetRewardsForPeriod_failure1() throws Exception {
        mockMvc.perform(get("/rewards/period")
                        .param("startMonth", "JANUARY")
                        .param("endMonth", "FEBRUARY")
                        .param("customerId", "id"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionClass").value("MethodArgumentTypeMismatchException"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when start/end month is missing")
    void testGetRewardsForPeriod_failure2() throws Exception {
        mockMvc.perform(get("/rewards/period")
                        .param("startMonth", "JANUARY")
                        .param("customerId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionClass").value("MissingServletRequestParameterException"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"));
    }

    @Test
    @DisplayName("Test getRewardsForPeriod when start/end month is invalid")
    void testGetRewardsForPeriod_failure3() throws Exception {
        mockMvc.perform(get("/rewards/period")
                        .param("startMonth", "JANUARY")
                        .param("endMonth", "FEB")
                        .param("customerId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionClass").value("MethodArgumentTypeMismatchException"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"));
    }
}
