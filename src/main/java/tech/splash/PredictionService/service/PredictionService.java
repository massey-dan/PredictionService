package tech.splash.PredictionService.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.splash.PredictionService.dto.WinnerPredictionDto;
import tech.splash.PredictionService.model.Match;
import tech.splash.PredictionService.model.Prediction;
import tech.splash.PredictionService.model.WinnerPrediction;
import tech.splash.PredictionService.repository.MatchRepository;
import tech.splash.PredictionService.repository.PredictionRepository;
import tech.splash.PredictionService.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public List<Prediction> getPredictionsByUser(Long userId) {
        return predictionRepository.findByUserId(userId);
    }

    @Transactional
    public WinnerPrediction createWinnerPrediction(WinnerPredictionDto predictionDto) {
        validateWinnerPrediction(predictionDto);

        getPrediction(predictionDto)
                .ifPresent(p -> {
                    throw new IllegalStateException("Prediction already found for this match and user");
                });

        validateMatch(predictionDto);

        userRepository.findById(predictionDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id"));

        WinnerPrediction prediction = WinnerPrediction.builder()
                .userId(predictionDto.getUserId())
                .matchId(predictionDto.getMatchId())
                .predictedWinner(predictionDto.getPredictedWinner())
                .build();

        return predictionRepository.save(prediction);
    }

    @Transactional
    public WinnerPrediction updateWinnerPrediction(WinnerPredictionDto predictionDto) {
        validateWinnerPrediction(predictionDto);

        Prediction prediction = getPrediction(predictionDto)
                .orElseThrow(() -> new EntityNotFoundException("Prediction not found"));


        if (!(prediction instanceof WinnerPrediction WinnerPrediction)) {
            throw new IllegalArgumentException("Invalid prediction type");
        }

        validateMatch(predictionDto);

        WinnerPrediction.setPredictedWinner(predictionDto.getPredictedWinner());
        return predictionRepository.save(WinnerPrediction);
    }

    private Optional<Prediction> getPrediction(WinnerPredictionDto predictionDto) {
        return predictionRepository.findByUserIdAndMatchId(predictionDto.getUserId(), predictionDto.getMatchId()).stream()
                .findFirst();
    }

    private void validateMatch(WinnerPredictionDto predictionDto) {
        Match match = matchRepository.findById(predictionDto.getMatchId())
                .orElseThrow(() -> new EntityNotFoundException("Match not found"));

        if (match.getTeams().stream().noneMatch(s -> predictionDto.getPredictedWinner().equals(s))) {
            throw new IllegalStateException("This predicted winner is not a team in this match");
        }

        if (match.hasStarted()) {
            throw new IllegalStateException("match has already started");
        }
    }

    private void validateWinnerPrediction(WinnerPredictionDto winnerPredictionDto) {

        StringBuilder errorMessage = new StringBuilder();

        if (winnerPredictionDto.getUserId() == null) {
            errorMessage.append("\"userId\" is a required field. ");
        }
        if (winnerPredictionDto.getMatchId() == null) {
            errorMessage.append("\"matchId\" is a required field. ");
        }
        if (winnerPredictionDto.getPredictedWinner() == null || winnerPredictionDto.getPredictedWinner().isBlank()) {
            errorMessage.append("\"predictedWinner\" is a required field. ");
        }
        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    }

}