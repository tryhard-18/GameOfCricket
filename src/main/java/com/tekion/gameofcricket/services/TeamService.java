package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.request.TeamRequest;
import com.tekion.gameofcricket.dto.response.TeamResponse;
import com.tekion.gameofcricket.entity.Team;

import java.util.List;

public interface TeamService {
    TeamResponse saveTeam(TeamRequest teamRequest);

    List<TeamResponse> getTeams();

    String deleteTeam(String teamName);

    TeamResponse getTeam(String teamName);

    TeamResponse updateTeam(String teamName, Team updatedTeam);
}
