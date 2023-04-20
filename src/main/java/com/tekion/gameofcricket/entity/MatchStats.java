package com.tekion.gameofcricket.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "matchStats")
public class MatchStats {
    @Id
    private String matchId;
    private TeamStats teamStats1;
    private TeamStats teamStats2;
    private int totalOvers;
    private int toss;
    private String result;

}
