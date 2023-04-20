package com.tekion.gameofcricket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "players")
//@CompoundIndexes(
//        @CompoundIndex(name = "pId_pN", def = "{'playerId': 1, 'playerName' : 1", unique = true)
//)
public class Player {
    @Id
    private String playerId;
    @Indexed
    private String playerName;
    @Indexed
    private String teamName;
    private PlayerType playerType;
    private int matchesPlayed;
    private int playerTotalRuns;
    private int playerTotalWickets;


}
