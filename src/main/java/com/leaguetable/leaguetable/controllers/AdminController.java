package com.leaguetable.leaguetable.controllers;

import com.leaguetable.leaguetable.models.Leagues;
import com.leaguetable.leaguetable.models.Standings;
import com.leaguetable.leaguetable.repository.LeagueRepository;
import com.leaguetable.leaguetable.repository.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LeagueRepository leaguesRepo;

    @Autowired
    private StandingsRepository standingsRepo;

    // Admin Dashboard
    @GetMapping("")
    public String adminDashboard() {
        return "admin";
    }

    // Create League Page
    @GetMapping("/create-league")
    public String createLeagueForm(Model model) {
        model.addAttribute("league", new Leagues());
        return "create-league";
    }

    // Create a new league
    @PostMapping("/create-league")
    public String createLeague(@ModelAttribute Leagues league, Model model) {
        if (leaguesRepo.existsById(league.getLeagueName())) {
            model.addAttribute("error", "League already exists.");
            return "create-league";
        }
        leaguesRepo.save(league);
        return "redirect:/leagues";
    }

    // Add Team Page
    @GetMapping("/add-team")
    public String addTeamForm(Model model) {
        model.addAttribute("team", new Standings());
        model.addAttribute("leagues", leaguesRepo.findAll()); // Fetch all leagues
        return "add-team";
    }


    // Add a team to a league
    @PostMapping("/add-team")
    public String addTeamToLeague(@RequestParam String leagueName, @ModelAttribute Standings team) {
        Leagues league = leaguesRepo.findById(leagueName)
                .orElseThrow(() -> new RuntimeException("League not found"));

        team.setLeague(league);
        standingsRepo.save(team);
        return "redirect:/tables/" + leagueName;
    }


    // Update Team Page
    @GetMapping("/update-team")
    public String updateTeamForm(Model model) {
        model.addAttribute("teams", standingsRepo.findAll()); // Fetch all teams
        return "update-team";
    }

    @PostMapping("/update-team")
    public String updateTeamStats(@RequestParam Long teamId, @ModelAttribute Standings updated) {
        Standings existing = standingsRepo.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        existing.setPoints(updated.getPoints());
        existing.setGamesPlayed(updated.getGamesPlayed());
        existing.setWins(updated.getWins());
        existing.setLosses(updated.getLosses());
        existing.setDraws(updated.getDraws());

        standingsRepo.save(existing);
        return "redirect:/tables/" + existing.getLeague().getLeagueName();
    }

}
