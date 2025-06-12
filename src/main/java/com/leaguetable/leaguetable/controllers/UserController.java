package com.leaguetable.leaguetable.controllers;

import com.leaguetable.leaguetable.models.Leagues;
import com.leaguetable.leaguetable.models.Standings;
import com.leaguetable.leaguetable.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final LeagueRepository repo;

    public UserController(LeagueRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/leagues")
    public String getAllLeagues(Model model) {
        model.addAttribute("leagues", repo.findAll());
        return "leagues";
    }

    @Autowired
    private LeagueRepository leaguesRepository;

    @GetMapping("/tables/{league_name}")
    public String getStandingsByLeague(@PathVariable String league_name, Model model) {
        Leagues league = leaguesRepository.findById(league_name)
                .orElseThrow(() -> new RuntimeException("League not found: " + league_name));

        // Sort teams in descending order of points
        List<Standings> sortedStandings = league.getStandings()
                .stream()
                .sorted(Comparator.comparingInt(Standings::getPoints).reversed())
                .collect(Collectors.toList());

        model.addAttribute("leagueName", league.getLeagueName());
        model.addAttribute("standings", sortedStandings);

        return "tables";
    }

}
