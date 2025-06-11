package com.leaguetable.leaguetable.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Leagues {
    @Id
    private String league_name;

    // Bi-directional mapping (optional)
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<Standings> standings;

    // Constructors, getters, setters

    public Leagues(String leagueName) {
        this.league_name = leagueName;
    }

    public Leagues() {

    }


    public String getLeagueName() {
        return league_name;
    }

    public void setLeagueName(String leagueName) {
        this.league_name = leagueName;
    }

    public List<Standings> getStandings() {
        return standings;
    }
}