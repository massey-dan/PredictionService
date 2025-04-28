package tech.splash.PredictionService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.splash.PredictionService.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // empty class

}