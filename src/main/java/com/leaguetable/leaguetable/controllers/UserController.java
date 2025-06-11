package com.leaguetable.leaguetable.controllers;


import com.leaguetable.leaguetable.models.Leagues;
import com.leaguetable.leaguetable.models.Standings;
import com.leaguetable.leaguetable.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final LeagueRepository repo;

    public UserController(LeagueRepository repo) {
        this.repo = repo;
    }


    @GetMapping("/leagues")
    public List<Leagues> getAllLeagues() {
        return repo.findAll();
    }

    @Autowired
    private LeagueRepository leaguesRepository;

    @GetMapping("tables/{leagueName}")
    public List<Standings> getStandingsByLeague(@PathVariable String leagueName) {
        Leagues league = leaguesRepository.findById(leagueName)
                .orElseThrow(() -> new RuntimeException("League not found: " + leagueName));
        return league.getStandings(); // this returns the list of teams with points etc.
    }




    @GetMapping("/")
    public String home(){
        return "homepage";
    }


//    @GetMapping("/leagues")
//    public String leagues(){
//        return "leagues";
//    }

    @GetMapping("/admin")
    public String admin(){
        return "logged in as admin";
    }
}
