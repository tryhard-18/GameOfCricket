package com.tekion.gameofcricket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BowlerInfo extends PlayerInfo {
    private int playerPerMatchWickets;
    private int playerPerMatchRunsConceded;

}
