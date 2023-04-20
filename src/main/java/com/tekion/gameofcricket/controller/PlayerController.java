package com.tekion.gameofcricket.controller;

import com.tekion.gameofcricket.entity.Player;
import com.tekion.gameofcricket.entity.PlayerInfo;
import com.tekion.gameofcricket.dto.request.PlayerRequest;
import com.tekion.gameofcricket.dto.response.PlayerResponse;
import com.tekion.gameofcricket.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("")
    public PlayerResponse savePlayer(@RequestBody PlayerRequest playerRequest){
        return playerService.savePlayer(playerRequest);
    }

    @GetMapping("match/{matchId}/player/{playerName}")
    public PlayerInfo getPlayerInfoOfAMatch(@PathVariable("matchId") String matchId, @PathVariable("playerName") String playerName){
        return playerService.getPlayerInfoOfAMatch(matchId, playerName);
    }

    @GetMapping("/{playerName}")
    public PlayerResponse getPlayer(@PathVariable("playerName") String playerName){
        return playerService.getPlayer(playerName);
    }

    @GetMapping("/team/{teamName}")
    public List<PlayerResponse> getPlayers(@PathVariable("teamName") String teamName){
        return playerService.getPlayers(teamName);
    }

    @PutMapping("/update/{playerName}")
    public PlayerResponse updatePlayer(@PathVariable("playerName") String playerName, @RequestBody Player updatedPlayer){
        return playerService.updatePlayer(playerName, updatedPlayer);
    }

    @DeleteMapping("/delete/{playerName}")
    public void deletePlayer(@PathVariable("playerName") String playerName){
        playerService.deletePlayer(playerName);
    }


}
