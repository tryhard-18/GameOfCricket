package com.tekion.gameofcricket.controller;

import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import com.tekion.gameofcricket.entity.InningsStats;
import com.tekion.gameofcricket.entity.MatchStats;
import com.tekion.gameofcricket.services.MatchStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/match-stats")
public class MatchStatsController{

    @Autowired
    private MatchStatsService matchStatsService;

    @PostMapping("/save")
    private MatchStatsResponse saveMatchStats(@RequestBody MatchStats matchStats){
        return matchStatsService.saveMatchStats(matchStats);
    }

    @GetMapping("/firstInnings/{matchId}")
    private InningsStats getFirstInningsOfAMatch(@PathVariable("matchId") String matchId){
        return matchStatsService.getFirstInningsOfAMatch(matchId);
    }

    @GetMapping("/secondInnings/{matchId}")
    private InningsStats getSecondInningsOfAMatch(@PathVariable("matchId") String matchId){
        return matchStatsService.getSecondInningsOfAMatch(matchId);
    }

    @GetMapping("/{matchId}")
    private MatchStatsResponse getMatchScorecard(@PathVariable("matchId") String matchId){
        return matchStatsService.getMatchScorecard(matchId);
    }

}
