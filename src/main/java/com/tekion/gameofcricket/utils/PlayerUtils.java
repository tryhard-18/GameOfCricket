package com.tekion.gameofcricket.utils;

import com.tekion.gameofcricket.entity.BatsmanInfo;
import com.tekion.gameofcricket.entity.BowlerInfo;
import com.tekion.gameofcricket.entity.PlayerInfo;
import com.tekion.gameofcricket.entity.Player;
import com.tekion.gameofcricket.dto.request.PlayerRequest;
import com.tekion.gameofcricket.dto.response.PlayerResponse;
import org.springframework.beans.BeanUtils;

public class PlayerUtils {
    public static Player convertPlayerReqToPlayer(PlayerRequest playerRequest){
        Player player = new Player();
        BeanUtils.copyProperties(playerRequest, player);
        return player;
    }

    public static PlayerResponse convertPlayerToPlayerRes(Player player){
        PlayerResponse playerResponse = new PlayerResponse();
        BeanUtils.copyProperties(player, playerResponse);
        return playerResponse;
    }

    public static PlayerInfo convertPlayerToBatsmanInfo(Player player){
        PlayerInfo batsman = new BatsmanInfo();
        BeanUtils.copyProperties(player, batsman);
        return batsman;
    }

    public static PlayerInfo convertPlayerToBowlerInfo(Player player){
        PlayerInfo bowler = new BowlerInfo();
        BeanUtils.copyProperties(player, bowler);
        return bowler;
    }

    public static PlayerInfo convertBowlerInfoToBatsmanInfo(PlayerInfo playerInfo){
        PlayerInfo batsman = new BatsmanInfo();
        BeanUtils.copyProperties(playerInfo, batsman);
        return batsman;
    }

    public static Player convertPlayerResponseToPlayer(PlayerResponse playerResponse){
        Player player = new Player();
        BeanUtils.copyProperties(playerResponse,player);
        return player;
    }
}
