package com.tekion.gameofcricket.dto.request;

import com.tekion.gameofcricket.entity.PlayerType;
import lombok.Data;

@Data
public class PlayerRequest {
    private String playerName;
    private String teamName;
    private PlayerType playerType;
}
