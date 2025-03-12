package com.myStore.rewardManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardsResponse {

    private Customer customerDetails;
    private Map<String, Double> rewardPoints;
}