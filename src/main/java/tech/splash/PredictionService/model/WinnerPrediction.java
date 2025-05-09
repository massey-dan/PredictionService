package tech.splash.PredictionService.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("WINNER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WinnerPrediction extends Prediction {

    @Column(nullable = false)
    private String predictedWinner;

}