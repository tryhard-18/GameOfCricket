package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.request.MatchRequest;
import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import com.tekion.gameofcricket.dto.response.PlayerResponse;
import com.tekion.gameofcricket.dto.response.TeamResponse;
import com.tekion.gameofcricket.entity.*;
import com.tekion.gameofcricket.error.ValidationException;
import com.tekion.gameofcricket.utils.PlayerUtils;
import com.tekion.gameofcricket.utils.TeamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchStatsService matchStatsService;

    @Override
    public MatchStatsResponse startMatch(MatchRequest matchRequest) {
        validateInput(matchRequest);
        return playMatch(matchRequest);
    }

    private void validateInput(MatchRequest matchRequest) {
        int toss = matchRequest.getToss();
        int overs = matchRequest.getTotalOvers();
        if (overs <= 0) {
            throw new ValidationException("Overs cannot be 0");
        }
        if (toss < 0 || toss > 1) {
            throw new ValidationException("Toss can either be 0 or 1");
        }
    }

    private MatchStatsResponse playMatch(MatchRequest matchRequest) {
        final TeamStats team1Stats = new TeamStats();
        team1Stats.setName(matchRequest.getTeam1Name());

        final TeamStats team2Stats = new TeamStats();
        team2Stats.setName(matchRequest.getTeam2Name());

        final int toss = matchRequest.getToss();
        final int overs = matchRequest.getTotalOvers();

        List<PlayerInfo> team1AllPlayerInfo = setTeamAllPlayerInfo(team1Stats.getName());
        List<PlayerInfo> team2AllPlayerInfo = setTeamAllPlayerInfo(team2Stats.getName());

        team1Stats.setTeamAllPlayerInfo(team1AllPlayerInfo);
        team2Stats.setTeamAllPlayerInfo(team2AllPlayerInfo);

        if (toss == 0) {
            playInning(team1Stats, team2Stats, Integer.MAX_VALUE, overs);
            playInning(team2Stats, team1Stats, team1Stats.getRunsScored(), overs);
        } else {
            playInning(team2Stats, team1Stats, Integer.MAX_VALUE, overs);
            playInning(team1Stats, team2Stats, team2Stats.getRunsScored(), overs);
        }
        List<PlayerInfo> allPlayers = new ArrayList<>(team1AllPlayerInfo);
        allPlayers.addAll(team2AllPlayerInfo);
        updatePlayerData(allPlayers);
        updateTeamsData(team1Stats, team2Stats);
        return setMatchStats(matchRequest, team1Stats, team2Stats);
    }

    private MatchStatsResponse setMatchStats(MatchRequest matchRequest, TeamStats team1Stats, TeamStats team2Stats) {
        MatchStats matchStats = new MatchStats();
        matchStats.setToss(matchRequest.getToss());
        matchStats.setTotalOvers(matchRequest.getTotalOvers());
        matchStats.setResult(result(team1Stats, team2Stats));
        matchStats.setTeamStats1(team1Stats);
        matchStats.setTeamStats2(team2Stats);
        return matchStatsService.saveMatchStats(matchStats);
    }

    private void playInning(TeamStats batTeamStats, TeamStats bowlTeamStats, int target, int overs) {
        List<PlayerInfo> listOfBatsman = batTeamStats.getTeamAllPlayerInfo();
        List<PlayerInfo> bowlTeamAllPlayerInfo = bowlTeamStats.getTeamAllPlayerInfo();
        List<PlayerInfo> listOfBowlers = getBowlers(bowlTeamAllPlayerInfo);
        int totalMembers = listOfBatsman.size();
        int noOfBowlers = listOfBowlers.size();
        int totalBalls = 0;
        int[] currBatsman = {0};
        int prevBowler = -1;
        int[] runsPerBatsman = {0};
        int currBowler = (int) (Math.random() * noOfBowlers);
        PlayerInfo bowler;
        do {
            while (currBowler == prevBowler) {
                currBowler = (int) (Math.random() * noOfBowlers);
            }
            bowler = listOfBowlers.get(currBowler);
            Over overResult = getOverResult(batTeamStats, runsPerBatsman, currBatsman, target);
            int ballsPerOver = overResult.getBalls();
            totalBalls = totalBalls + ballsPerOver;
            updateBowlerStats(overResult, bowler);
            prevBowler = currBowler;
        } while (batTeamStats.getRunsScored() <= target && batTeamStats.getWickets() != totalMembers && totalBalls != overs * 6);
        if (currBatsman[0] != listOfBatsman.size()) {
            listOfBatsman.get(currBatsman[0]).setPlayerPerMatchRuns(runsPerBatsman[0]);
        }
        updateBatTeamStats(batTeamStats, totalBalls);
    }

    private void updateBatTeamStats(TeamStats batTeamStats, int totalBalls) {
        int overPlayed = totalBalls / 6;
        int remainingBalls = totalBalls % 6;
        batTeamStats.setOversPlayed(overPlayed + "." + remainingBalls);
    }

    private void updateBowlerStats(Over overResult, PlayerInfo bowler) {
        int runsConcededPerOver = overResult.getConcededRuns();
        int wicketsPerOver = overResult.getWickets();
        if (bowler instanceof BowlerInfo) {
            BowlerInfo bowlerInfo = (BowlerInfo) bowler;
            bowlerInfo.setPlayerPerMatchRunsConceded(bowlerInfo.getPlayerPerMatchRunsConceded() + runsConcededPerOver);
            bowlerInfo.setPlayerPerMatchWickets(bowlerInfo.getPlayerPerMatchWickets() + wicketsPerOver);
        }
    }

    private Over getOverResult(TeamStats batTeamStats, int[] runPerBatsman, int[] currBatsman, int target) {
        List<PlayerInfo> listOfBatsman = batTeamStats.getTeamAllPlayerInfo();
        PlayerInfo batsman = listOfBatsman.get(currBatsman[0]);
        int ballsPerOver = 0;
        int totalMembers = listOfBatsman.size();
        Over over = new Over();
        while (batTeamStats.getRunsScored() <= target && ballsPerOver != 6 && batTeamStats.getWickets() != totalMembers) {
            int val = (int) (Math.random() * 8);
            if (val == 7) {
                wicketTaken(batsman, runPerBatsman, over, batTeamStats);
                batsman = getNextBatsman(listOfBatsman, currBatsman);
            } else {
                playShot(runPerBatsman, over, batTeamStats);
            }
            ballsPerOver++;
        }
        over.setBalls(ballsPerOver);
        return over;
    }

    private void wicketTaken(PlayerInfo batsman, int[] runPerBatsman, Over over, TeamStats batTeamStats) {
        batsman.setPlayerPerMatchRuns(runPerBatsman[0]);
        runPerBatsman[0] = 0;
        over.setWickets(over.getWickets() + 1);
        batTeamStats.setWickets(batTeamStats.getWickets() + 1);
    }

    private void playShot(int[] runPerBatsman, Over over, TeamStats batTeamStats) {
        int runPerBall = (int) (Math.random() * 7);
        runPerBatsman[0] = runPerBatsman[0] + runPerBall;
        over.setConcededRuns(over.getConcededRuns() + runPerBall);
        batTeamStats.setRunsScored(batTeamStats.getRunsScored() + runPerBall);
    }

    private PlayerInfo getNextBatsman(List<PlayerInfo> listOfBatsman, int[] currBatsman) {
        PlayerInfo batsman = listOfBatsman.get(currBatsman[0]);
        currBatsman[0] = currBatsman[0] + 1;
        if (validBatsman(currBatsman[0], listOfBatsman)) {
            batsman = listOfBatsman.get(currBatsman[0]);
        }
        return batsman;
    }

    private boolean validBatsman(int ind, List<PlayerInfo> listOfBatsman) {
        return ind < listOfBatsman.size();
    }

    private String result(TeamStats teamStats1, TeamStats teamStats2) {
        if (teamStats1.getRunsScored() > teamStats2.getRunsScored()) {
            return ("Winning team is " + teamStats1.getName());
        } else if (teamStats1.getRunsScored() < teamStats2.getRunsScored()) {
            return ("Winning team is " + teamStats2.getName());
        } else {
            return ("Match is drawn");
        }
    }

    private void updatePlayerData(List<PlayerInfo> allPlayers) {
        for (PlayerInfo playerInfo : allPlayers) {
            PlayerResponse playerResponse = playerService.getPlayer(playerInfo.getPlayerName());
            Player player = PlayerUtils.convertPlayerResponseToPlayer(playerResponse);
            player.setMatchesPlayed(player.getMatchesPlayed() + 1);
            player.setPlayerTotalRuns(player.getPlayerTotalRuns() + playerInfo.getPlayerPerMatchRuns());
            if (playerInfo instanceof BowlerInfo) {
                BowlerInfo bowlerInfo = (BowlerInfo) playerInfo;
                player.setPlayerTotalWickets(player.getPlayerTotalWickets() + bowlerInfo.getPlayerPerMatchWickets());
            }
            playerService.updatePlayer(player.getPlayerName(), player);
        }
    }

    private void updateTeamsData(TeamStats teamStats1, TeamStats teamStats2) {
        TeamResponse team1Response = teamService.getTeam(teamStats1.getName());
        TeamResponse team2Response = teamService.getTeam(teamStats2.getName());
        Team winningTeam;
        Team losingTeam;
        if (teamStats1.getRunsScored() > teamStats2.getRunsScored() || teamStats2.getRunsScored() > teamStats1.getRunsScored()) {
            if (teamStats1.getRunsScored() > teamStats2.getRunsScored()) {
                winningTeam = TeamUtils.convertTeamResponseToTeam(team1Response);
                losingTeam = TeamUtils.convertTeamResponseToTeam(team2Response);
            } else {
                winningTeam = TeamUtils.convertTeamResponseToTeam(team2Response);
                losingTeam = TeamUtils.convertTeamResponseToTeam(team1Response);
            }
            winningTeam.setMatchesWon(winningTeam.getMatchesWon() + 1);
            losingTeam.setMatchesLoss(losingTeam.getMatchesLoss() + 1);
        } else {
            winningTeam = TeamUtils.convertTeamResponseToTeam(team1Response);
            losingTeam = TeamUtils.convertTeamResponseToTeam(team2Response);
            winningTeam.setMatchesDrawn(winningTeam.getMatchesDrawn() + 1);
            losingTeam.setMatchesDrawn(losingTeam.getMatchesDrawn() + 1);
        }
        winningTeam.setTotalMatches(winningTeam.getTotalMatches() + 1);
        losingTeam.setTotalMatches(losingTeam.getTotalMatches() + 1);
        teamService.updateTeam(winningTeam.getTeamName(), winningTeam);
        teamService.updateTeam(losingTeam.getTeamName(), losingTeam);

    }

    private List<PlayerInfo> setTeamAllPlayerInfo(String teamName) {
        List<PlayerInfo> teamAllPlayerInfo = new ArrayList<>();
        for (PlayerResponse playerResponse : playerService.getPlayers(teamName)) {
            if (playerResponse.getPlayerType().equals(PlayerType.BATSMAN)) {
                teamAllPlayerInfo.add(PlayerUtils.convertPlayerToBatsmanInfo(PlayerUtils.convertPlayerResponseToPlayer(playerResponse)));
            } else {
                teamAllPlayerInfo.add(PlayerUtils.convertPlayerToBowlerInfo(PlayerUtils.convertPlayerResponseToPlayer(playerResponse)));
            }
        }
        return teamAllPlayerInfo;
    }

    private List<PlayerInfo> getBowlers(List<PlayerInfo> bowlTeamAllPlayerInfo) {
        List<PlayerInfo> listOfBowlers = new ArrayList<>();
        for (PlayerInfo playerInfo : bowlTeamAllPlayerInfo) {
            if (playerInfo.getPlayerType().equals(PlayerType.BOWLER)) {
                listOfBowlers.add(playerInfo);
            }
        }
        return listOfBowlers;
    }

}



