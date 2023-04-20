package com.tekion.gameofcricket.repository;

import com.tekion.gameofcricket.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    List<Player> findAllByTeamName(String teamName);

    Player getByPlayerName(String playerName);



}
