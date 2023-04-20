package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import com.tekion.gameofcricket.entity.InningsStats;
import com.tekion.gameofcricket.entity.MatchStats;
import com.tekion.gameofcricket.entity.PlayerInfo;
import com.tekion.gameofcricket.entity.PlayerType;
import com.tekion.gameofcricket.repository.MatchStatsRepository;
import com.tekion.gameofcricket.utils.MatchStatsUtils;
import com.tekion.gameofcricket.utils.PlayerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchStatsServiceImpl implements MatchStatsService {
    @Autowired
    private MatchStatsRepository matchStatsRepository;

    @Override
    public MatchStatsResponse saveMatchStats(MatchStats matchStats) {
        matchStatsRepository.save(matchStats);
        return MatchStatsUtils.convertMatchStatsToMatchStatsRes(matchStats);
    }

    @Override
    public InningsStats getFirstInningsOfAMatch(String matchId) {
        MatchStats matchStats = matchStatsRepository.findById(matchId).get();
        InningsStats inningsStats = new InningsStats();
        List<PlayerInfo> firstInningsPlayersInfo;
        if (matchStats.getToss() == 1) {
            firstInningsPlayersInfo = settingInningsStats(matchStats.getTeamStats2().getName(), matchStats.getTeamStats1().getName(), inningsStats, matchStats.getTeamStats2().getTeamAllPlayerInfo(), matchStats.getTeamStats1().getTeamAllPlayerInfo());
            inningsStats.setBattingTeamScore(matchStats.getTeamStats2().getName() + ":" + matchStats.getTeamStats2().getRunsScored() + "/" + matchStats.getTeamStats2().getWickets() + " in " + matchStats.getTeamStats2().getOversPlayed());
        } else {
            firstInningsPlayersInfo = settingInningsStats(matchStats.getTeamStats1().getName(), matchStats.getTeamStats2().getName(), inningsStats, matchStats.getTeamStats1().getTeamAllPlayerInfo(), matchStats.getTeamStats2().getTeamAllPlayerInfo());
            inningsStats.setBattingTeamScore(matchStats.getTeamStats1().getName() + ":" + matchStats.getTeamStats1().getRunsScored() + "/" + matchStats.getTeamStats1().getWickets() + " in " + matchStats.getTeamStats1().getOversPlayed());
        }
        inningsStats.setPlayersInfo(firstInningsPlayersInfo);
        return inningsStats;

    }

    @Override
    public InningsStats getSecondInningsOfAMatch(String matchId) {
        MatchStats matchStats = matchStatsRepository.findById(matchId).get();
        InningsStats inningsStats = new InningsStats();
        List<PlayerInfo> secondInningsPlayersInfo;
        if (matchStats.getToss() == 1) {
            secondInningsPlayersInfo = settingInningsStats(matchStats.getTeamStats1().getName(), matchStats.getTeamStats2().getName(), inningsStats, matchStats.getTeamStats1().getTeamAllPlayerInfo(), matchStats.getTeamStats2().getTeamAllPlayerInfo());
            inningsStats.setBattingTeamScore(matchStats.getTeamStats1().getName() + ":" + matchStats.getTeamStats1().getRunsScored() + "/" + matchStats.getTeamStats1().getWickets() + " in " + matchStats.getTeamStats1().getOversPlayed());
        } else {
            secondInningsPlayersInfo = settingInningsStats(matchStats.getTeamStats2().getName(), matchStats.getTeamStats1().getName(), inningsStats, matchStats.getTeamStats2().getTeamAllPlayerInfo(), matchStats.getTeamStats1().getTeamAllPlayerInfo());
            inningsStats.setBattingTeamScore(matchStats.getTeamStats2().getName() + ":" + matchStats.getTeamStats2().getRunsScored() + "/" + matchStats.getTeamStats2().getWickets() + " in " + matchStats.getTeamStats2().getOversPlayed());
        }
        inningsStats.setPlayersInfo(secondInningsPlayersInfo);
        return inningsStats;
    }

    @Override
    public MatchStatsResponse getMatchScorecard(String matchId) {
        MatchStats matchStats = matchStatsRepository.findById(matchId).get();
        return MatchStatsUtils.convertMatchStatsToMatchStatsRes(matchStats);
    }

    private List<PlayerInfo> settingInningsStats(String batTeam, String bowlTeam, InningsStats inningsStats, List<PlayerInfo> batTeamAllPlayerInfo, List<PlayerInfo> bowlTeamAllPlayerInfo) {
        List<PlayerInfo> firstInningsPlayersInfo = new ArrayList<>();
        inningsStats.setBattingTeam(batTeam);
        inningsStats.setBowlingTeam(bowlTeam);
        for (PlayerInfo playerInfo : batTeamAllPlayerInfo) {
            if (playerInfo.getPlayerType().equals(PlayerType.BOWLER)) {
                firstInningsPlayersInfo.add(PlayerUtils.convertBowlerInfoToBatsmanInfo(playerInfo));
            } else {
                firstInningsPlayersInfo.add(playerInfo);
            }
        }
        for (PlayerInfo playerInfo : bowlTeamAllPlayerInfo) {
            if (playerInfo.getPlayerType().equals(PlayerType.BOWLER)) {
                firstInningsPlayersInfo.add(playerInfo);
            }
        }
        return firstInningsPlayersInfo;
    }


}
