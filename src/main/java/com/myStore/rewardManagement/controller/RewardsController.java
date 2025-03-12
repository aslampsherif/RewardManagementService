package com.myStore.rewardManagement.controller;

import com.myStore.rewardManagement.dto.RewardsResponse;
import com.myStore.rewardManagement.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/rewards")
@AllArgsConstructor
public class RewardsController {

    private final RewardsService rewardsService;

    @GetMapping
    @Operation(summary = "Endpoint to retrieve customer's reward point",
            description = "Retrieves total rewards of each/all customers for all/specified months")
    public ResponseEntity<List<RewardsResponse>> getRewards(
            @RequestParam(name = "months", required = false) List<Month> months,
            @RequestParam(name = "customerId", required = false, defaultValue = "0") int customerId) {
        return ResponseEntity.ok(rewardsService.getRewards(months, customerId));
    }

    @GetMapping("/period")
    @Operation(summary = "Endpoint to retrieve customer's reward point for a time period",
            description = "Retrieves total rewards of each/all customers for specified time period")
    public ResponseEntity<List<RewardsResponse>> getRewardsForPeriod(
            @RequestParam(name = "startMonth") Month startMonth,
            @RequestParam(name = "endMonth") Month endMonth,
            @RequestParam(name = "customerId", required = false, defaultValue = "0") int customerId) {
        return ResponseEntity.ok(rewardsService.getRewardsForPeriod(customerId, startMonth, endMonth));
    }
}