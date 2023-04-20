package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.request.MatchRequest;
import com.tekion.gameofcricket.dto.response.MatchStatsResponse;

public interface MatchService {
    MatchStatsResponse startMatch(MatchRequest matchRequest);

}
