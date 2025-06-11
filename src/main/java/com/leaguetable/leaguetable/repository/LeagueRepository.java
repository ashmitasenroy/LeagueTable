package com.leaguetable.leaguetable.repository;

import com.leaguetable.leaguetable.models.Leagues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<Leagues, String> {
}
