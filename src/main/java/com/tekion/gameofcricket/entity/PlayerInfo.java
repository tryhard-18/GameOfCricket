package com.tekion.gameofcricket.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.EXISTING_PROPERTY,
//        property = "playerType")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Batsman.class, name = "Batsman"),
//        @JsonSubTypes.Type(value = Bowler.class, name = "Bowler")
//})
@NoArgsConstructor
@AllArgsConstructor
public abstract class PlayerInfo {
    private String playerName;
    private String teamName;
    private PlayerType playerType;
    private int playerPerMatchRuns;

//    public abstract String getPlayerType();
}
