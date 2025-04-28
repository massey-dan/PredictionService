package tech.splash.PredictionService.dto;

import lombok.Data;

@Data
public class WinnerPredictionDto {
    protected Long matchId;
    protected Long userId;
    private String predictedWinner;
}
