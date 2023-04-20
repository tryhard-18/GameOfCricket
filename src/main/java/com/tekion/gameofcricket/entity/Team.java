package com.tekion.gameofcricket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "teams")
public class Team {
    @Id
    private String teamName;
    private int totalMatches;
    private int matchesWon;
    private int matchesLoss;
    private int matchesDrawn;

}
