package com.leaguetable.leaguetable.repository;

import com.leaguetable.leaguetable.models.Standings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandingsRepository extends JpaRepository<Standings, Long> {
}
