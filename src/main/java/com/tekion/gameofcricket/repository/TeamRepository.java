package com.tekion.gameofcricket.repository;

import com.tekion.gameofcricket.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {

}
