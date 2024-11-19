package com.mundia.msutilisateur.web;

import com.mundia.msutilisateur.dto.UserReq;
import com.mundia.msutilisateur.entities.User;
import com.mundia.msutilisateur.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Add a new user
    @PostMapping("/add")
    public User addUser(@RequestBody UserReq userReq) {
        return userService.addUser(userReq);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get users by name
    @GetMapping("/name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        return userService.getUsersByName(name);
    }

    // Update a user
    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserReq userReq) {
        return userService.updateUser(id, userReq);
    }

    // Delete a user
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
