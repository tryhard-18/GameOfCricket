package com.tekion.gameofcricket.repository;

import com.tekion.gameofcricket.entity.MatchStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchStatsRepository extends MongoRepository<MatchStats, String> {
}
