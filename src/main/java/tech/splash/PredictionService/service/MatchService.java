package tech.splash.PredictionService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.splash.PredictionService.dto.MatchDto;
import tech.splash.PredictionService.model.Match;
import tech.splash.PredictionService.repository.MatchRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public List<Match> getMatches() {
        return matchRepository.findAll();
    }

    @Transactional
    public Match createMatch(MatchDto matchDto) {

        validateMatch(matchDto);

        Match match = Match.builder()
                .teams(matchDto.getTeams())
                .startTime(matchDto.getStartTime())
                .build();

        return matchRepository.save(match);
    }

    private void validateMatch(MatchDto matchDto) {

        StringBuilder errorMessage = new StringBuilder();

        if (matchDto.getTeams() == null
                || matchDto.getTeams().size() < 2
                || matchDto.getTeams().stream().anyMatch(String::isBlank)) {
            errorMessage.append("there must be at least 2 \"teams\", and they cannot be blank. ");
        }
        if (matchDto.getStartTime() == null) {
            errorMessage.append("\"startTime\" is a required field. ");
        } else if (!matchDto.getStartTime().isAfter(LocalDateTime.now())) {
            errorMessage.append("\"startTime\" must be in the future. ");
        }

        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    }

}