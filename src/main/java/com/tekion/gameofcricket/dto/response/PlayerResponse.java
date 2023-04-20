package com.tekion.gameofcricket.dto.response;

import com.tekion.gameofcricket.entity.PlayerType;
import lombok.Data;

@Data
public class PlayerResponse {
    private String playerId;
    private String playerName;
    private String teamName;
    private PlayerType playerType;
    private int matchesPlayed;
    private int playerTotalRuns;
    private int playerTotalWickets;
}
