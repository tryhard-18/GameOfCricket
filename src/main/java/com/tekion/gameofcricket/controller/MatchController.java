package com.tekion.gameofcricket.controller;

import com.tekion.gameofcricket.dto.request.MatchRequest;
import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import com.tekion.gameofcricket.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;
    @PostMapping("/start-match")
    private MatchStatsResponse startMatch(@RequestBody MatchRequest matchRequest){
        return matchService.startMatch(matchRequest);
    }

}
