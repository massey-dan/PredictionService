package tech.splash.PredictionService.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void hasStarted_past() {
        final var pastMatchTest = Match.builder()
                .startTime(LocalDateTime.now().minusDays(1))
                .build();
        assertTrue(pastMatchTest.hasStarted());
    }

    @Test
    void hasStarted_future() {
        final var futureMatchTest = Match.builder()
                .startTime(LocalDateTime.now().plusDays(1))
                .build();
        assertFalse(futureMatchTest.hasStarted());
    }
}