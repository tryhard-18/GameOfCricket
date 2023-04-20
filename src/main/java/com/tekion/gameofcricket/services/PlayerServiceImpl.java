package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.response.MatchStatsResponse;
import com.tekion.gameofcricket.entity.PlayerInfo;
import com.tekion.gameofcricket.entity.MatchStats;
import com.tekion.gameofcricket.entity.Player;
import com.tekion.gameofcricket.repository.PlayerRepository;
import com.tekion.gameofcricket.dto.request.PlayerRequest;
import com.tekion.gameofcricket.dto.response.PlayerResponse;
import com.tekion.gameofcricket.utils.MatchStatsUtils;
import com.tekion.gameofcricket.utils.PlayerUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchStatsService matchStatsService;
    @Override
    public PlayerResponse savePlayer(PlayerRequest playerRequest) {
        Player player = PlayerUtils.convertPlayerReqToPlayer(playerRequest);
        playerRepository.save(player);
        return PlayerUtils.convertPlayerToPlayerRes(player);
    }

    @Override
    public List<PlayerResponse> getPlayers(String teamName) {
        List<Player> players = playerRepository.findAllByTeamName(teamName);
        List<PlayerResponse> playersRes = new ArrayList<>();
        for(Player player: players){
            playersRes.add(PlayerUtils.convertPlayerToPlayerRes(player));
        }
        return playersRes;
    }

    @Override
    public PlayerResponse getPlayer(String playerName) {
        Player player = playerRepository.getByPlayerName(playerName);
        return PlayerUtils.convertPlayerToPlayerRes(player);
    }

    @Override
    public PlayerInfo getPlayerInfoOfAMatch(String matchId, String playerName) {
        MatchStatsResponse matchStatsResponse = matchStatsService.getMatchScorecard(matchId);
        MatchStats matchStats = MatchStatsUtils.convertMatchStatsResToMatchStats(matchStatsResponse);
        List<PlayerInfo> teamsAllPlayerInfo = matchStats.getTeamStats1().getTeamAllPlayerInfo();
        teamsAllPlayerInfo.addAll(matchStats.getTeamStats2().getTeamAllPlayerInfo());
        for(PlayerInfo playerInfo:teamsAllPlayerInfo){
            if(playerInfo.getPlayerName().equals(playerName)){
                return playerInfo;
            }
        }
        return null;

    }

    @Override
    public PlayerResponse updatePlayer(String playerName, Player updatedPlayer) {
        Player player = playerRepository.getByPlayerName(playerName);
        BeanUtils.copyProperties(updatedPlayer, player);
        playerRepository.save(player);
        return PlayerUtils.convertPlayerToPlayerRes(player);
    }

    @Override
    public void deletePlayer(String playerName) {
        Player player = playerRepository.getByPlayerName(playerName);
        playerRepository.delete(player);
    }

}
