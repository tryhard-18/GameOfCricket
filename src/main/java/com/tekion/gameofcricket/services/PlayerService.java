package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.entity.Player;
import com.tekion.gameofcricket.entity.PlayerInfo;
import com.tekion.gameofcricket.dto.request.PlayerRequest;
import com.tekion.gameofcricket.dto.response.PlayerResponse;

import java.util.List;

public interface PlayerService {
    PlayerResponse savePlayer(PlayerRequest playerRequest);
    List<PlayerResponse> getPlayers(String teamName);
    PlayerResponse getPlayer(String playerName);
    PlayerInfo getPlayerInfoOfAMatch(String matchId, String playerName);

    PlayerResponse updatePlayer(String playerName, Player updatedPlayer);

    void deletePlayer(String playerName);
}
