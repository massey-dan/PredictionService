package tech.splash.PredictionService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.splash.PredictionService.dto.MatchDto;
import tech.splash.PredictionService.model.Match;
import tech.splash.PredictionService.service.MatchService;

import java.util.List;

import static tech.splash.PredictionService.utils.JsonUtils.writeAsString;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    // Create a match
    @PostMapping
    public ResponseEntity<String> createMatch(@RequestBody MatchDto match) {
        try {
            Match createdMatch = matchService.createMatch(match);
            return new ResponseEntity<>(writeAsString(createdMatch), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Error creating match: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating match: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all matches
    @GetMapping
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = matchService.getMatches();
        return ResponseEntity.ok(matches);
    }

}