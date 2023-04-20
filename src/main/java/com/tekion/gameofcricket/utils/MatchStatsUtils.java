package com.tekion.gameofcricket.utils;

import com.tekion.gameofcricket.entity.MatchStats;
import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import org.springframework.beans.BeanUtils;

public class MatchStatsUtils {

    public static MatchStatsResponse convertMatchStatsToMatchStatsRes(MatchStats matchStats){
        MatchStatsResponse matchStatsResponse = new MatchStatsResponse();
        BeanUtils.copyProperties(matchStats, matchStatsResponse);
        if(matchStats.getToss()==1){
            matchStatsResponse.setToss("Toss is won by "+ matchStats.getTeamStats2().getName()+" and choose to bat");
        }
        else{
            matchStatsResponse.setToss("Toss is won by "+ matchStats.getTeamStats1().getName()+" and choose to bat");
        }
        return matchStatsResponse;
    }

    public static MatchStats convertMatchStatsResToMatchStats(MatchStatsResponse matchStatsResponse){
        MatchStats matchStats = new MatchStats();
        BeanUtils.copyProperties(matchStatsResponse, matchStats);
        if(matchStatsResponse.getToss().equals("Toss is won by "+ matchStats.getTeamStats2().getName()+" and choose to bat")){
            matchStats.setToss(1);
        }
        else{
            matchStats.setToss(0);
        }
        return matchStats;
    }

}
