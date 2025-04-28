package tech.splash.PredictionService.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.splash.PredictionService.dto.WinnerPredictionDto;
import tech.splash.PredictionService.model.Prediction;
import tech.splash.PredictionService.model.WinnerPrediction;
import tech.splash.PredictionService.service.PredictionService;

import java.util.List;

import static tech.splash.PredictionService.utils.JsonUtils.writeAsString;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    // Create a winner prediction
    @PostMapping("/winner")
    public ResponseEntity<String> createWinnerPrediction(
            @RequestBody WinnerPredictionDto predictionDto) {
        try {
            WinnerPrediction prediction = predictionService.createWinnerPrediction(predictionDto);
            return new ResponseEntity<>(writeAsString(prediction), HttpStatus.CREATED);
        } catch (IllegalStateException | EntityNotFoundException e) {
            return new ResponseEntity<>("Error creating prediction: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating match: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a winner prediction
    @PutMapping("/winner")
    public ResponseEntity<String> updateWinnerPrediction(
            @RequestBody WinnerPredictionDto predictionDto) {
        try {
            WinnerPrediction prediction = predictionService.updateWinnerPrediction(predictionDto);
            return new ResponseEntity<>(writeAsString(prediction), HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            return new ResponseEntity<>("Error updating prediction: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating match: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Get all predictions by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Prediction>> getPredictionsByUser(@PathVariable Long userId) {
        List<Prediction> predictions = predictionService.getPredictionsByUser(userId);
        return ResponseEntity.ok(predictions);
    }
}
