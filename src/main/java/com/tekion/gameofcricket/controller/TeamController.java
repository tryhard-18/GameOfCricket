package com.tekion.gameofcricket.controller;

import com.tekion.gameofcricket.dto.request.TeamRequest;
import com.tekion.gameofcricket.dto.response.TeamResponse;
import com.tekion.gameofcricket.entity.Team;
import com.tekion.gameofcricket.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public TeamResponse saveTeam(@RequestBody TeamRequest teamRequest){
        return teamService.saveTeam(teamRequest);
    }

    @GetMapping("/{teamName}")
    public TeamResponse getTeam(@PathVariable("teamName") String teamName){
        return teamService.getTeam(teamName);
    }

    @DeleteMapping("/{teamName}")
    public String deleteTeam(@PathVariable("teamName") String teamName){
        return teamService.deleteTeam(teamName);
    }

    @GetMapping("/teams")
    public List<TeamResponse> getTeams(){
        return teamService.getTeams();
    }

    @PutMapping("/update/{teamName}")
    public TeamResponse updateTeam(@PathVariable("teamName") String teamName, Team updatedTeam){
        return teamService.updateTeam(teamName, updatedTeam);
    }

}
