package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import com.tekion.gameofcricket.entity.InningsStats;
import com.tekion.gameofcricket.entity.MatchStats;

public interface MatchStatsService {
//    MatchStatsResponse startMatch(MatchStatsRequest matchStatsRequest);
    InningsStats getFirstInningsOfAMatch(String matchId);

    InningsStats getSecondInningsOfAMatch(String matchId);

    MatchStatsResponse getMatchScorecard(String matchId);

    MatchStatsResponse saveMatchStats(MatchStats matchStats);

}
