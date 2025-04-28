package tech.splash.PredictionService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.splash.PredictionService.dto.UserDto;
import tech.splash.PredictionService.model.User;
import tech.splash.PredictionService.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(UserDto userDto) {

        if (userDto.getUsername() == null || userDto.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is a required field");
        }

        if (!userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("a user with this username already exists");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .build();

        return userRepository.save(user);
    }

}