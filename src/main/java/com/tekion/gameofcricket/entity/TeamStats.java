package com.tekion.gameofcricket.entity;

import lombok.Data;

import java.util.List;

@Data
public class TeamStats {
    private String name;
    private int runsScored;
    private int wickets;
    private String oversPlayed;
    private List<PlayerInfo> teamAllPlayerInfo;

}
