package com.tekion.gameofcricket.dto.response;

import lombok.Data;

@Data
public class TeamResponse {
    private String teamName;
    private int totalMatches;
    private int matchesWon;
    private int matchesLoss;
    private int matchesDrawn;

}
