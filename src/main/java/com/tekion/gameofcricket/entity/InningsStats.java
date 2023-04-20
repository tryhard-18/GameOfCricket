package com.tekion.gameofcricket.entity;

import lombok.Data;

import java.util.List;

@Data
public class InningsStats {
    private String battingTeam;
    private String bowlingTeam;
    private String battingTeamScore;
    private List<PlayerInfo> playersInfo;
}
