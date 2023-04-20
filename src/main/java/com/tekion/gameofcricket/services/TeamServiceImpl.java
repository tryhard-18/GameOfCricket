package com.tekion.gameofcricket.services;

import com.tekion.gameofcricket.dto.response.PlayerResponse;
import com.tekion.gameofcricket.entity.Team;
import com.tekion.gameofcricket.repository.TeamRepository;
import com.tekion.gameofcricket.dto.request.TeamRequest;
import com.tekion.gameofcricket.dto.response.TeamResponse;
import com.tekion.gameofcricket.utils.TeamUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerService playerService;

    @Override
    public TeamResponse saveTeam(TeamRequest teamRequest) {
        Team team = TeamUtils.convertTeamReqToTeam(teamRequest);
        teamRepository.save(team);
        return TeamUtils.convertTeamToTeamRes(team);
    }

    @Override
    public List<TeamResponse> getTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamResponse> teamsRes = new ArrayList<>();
        for(Team team : teams){
            teamsRes.add(TeamUtils.convertTeamToTeamRes(team));
        }
        return teamsRes;
    }

    @Override
    public String deleteTeam(String teamName) {
        List<PlayerResponse> players = playerService.getPlayers(teamName);
        for (PlayerResponse player:players){
            playerService.deletePlayer(player.getPlayerName());
        }
        teamRepository.deleteById(teamName);
        return "Team Deleted Successfully";
    }

    @Override
    public TeamResponse getTeam(String teamName) {
        Team team = teamRepository.findById(teamName).get();
        return TeamUtils.convertTeamToTeamRes(team);

    }

    @Override
    public TeamResponse updateTeam(String teamName, Team updatedTeam) {
        Team team = teamRepository.findById(teamName).get();
        BeanUtils.copyProperties(updatedTeam, team);
        teamRepository.save(team);
        return TeamUtils.convertTeamToTeamRes(team);

    }
}
