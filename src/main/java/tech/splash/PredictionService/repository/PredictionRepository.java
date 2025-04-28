package tech.splash.PredictionService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.splash.PredictionService.model.Prediction;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findByUserId(Long userId);

    List<Prediction> findByUserIdAndMatchId(Long userId, Long matchId);

}