package com.tekion.gameofcricket.utils;

import com.tekion.gameofcricket.entity.Team;
import com.tekion.gameofcricket.dto.request.TeamRequest;
import com.tekion.gameofcricket.dto.response.TeamResponse;
import org.springframework.beans.BeanUtils;

public class TeamUtils {
    public static Team convertTeamReqToTeam(TeamRequest teamRequest){
        Team team = new Team();
        BeanUtils.copyProperties(teamRequest, team);
        return team;
    }

    public static TeamResponse convertTeamToTeamRes(Team team){
        TeamResponse teamResponse = new TeamResponse();
        BeanUtils.copyProperties(team, teamResponse);
        return teamResponse;
    }

    public static Team convertTeamResponseToTeam(TeamResponse teamResponse){
        Team team = new Team();
        BeanUtils.copyProperties(teamResponse, team);
        return team;
    }
}
