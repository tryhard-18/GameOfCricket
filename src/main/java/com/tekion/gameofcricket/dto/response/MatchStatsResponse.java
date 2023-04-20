package com.tekion.gameofcricket.dto.response;

import com.tekion.gameofcricket.entity.TeamStats;
import lombok.Data;

@Data
public class MatchStatsResponse {
    private String matchId;
    private TeamStats teamStats1;
    private TeamStats teamStats2;
    private int totalOvers;
    private String toss;
    private String result;


}
