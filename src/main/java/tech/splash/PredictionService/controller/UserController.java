package tech.splash.PredictionService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.splash.PredictionService.dto.UserDto;
import tech.splash.PredictionService.model.User;
import tech.splash.PredictionService.service.UserService;
import tech.splash.PredictionService.utils.JsonUtils;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create a user
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(JsonUtils.writeAsString(createdUser), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

}