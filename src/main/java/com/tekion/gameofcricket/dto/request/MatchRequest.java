package com.tekion.gameofcricket.dto.request;

import lombok.Data;

@Data
public class MatchRequest {
    private final String team1Name;
    private final String team2Name;
    private final int totalOvers;
    private final int toss;
}
