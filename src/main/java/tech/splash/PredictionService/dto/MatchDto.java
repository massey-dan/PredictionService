package tech.splash.PredictionService.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchDto {
    private long id;
    private List<String> teams;
    private LocalDateTime startTime;
}
