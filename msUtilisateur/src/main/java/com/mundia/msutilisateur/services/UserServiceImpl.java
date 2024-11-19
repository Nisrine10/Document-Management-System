package com.mundia.msutilisateur.services;

import com.mundia.msutilisateur.dto.UserReq;
import com.mundia.msutilisateur.entities.User;
import com.mundia.msutilisateur.mappers.UserMapper;
import com.mundia.msutilisateur.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userRepo.findAllByNom(name);
    }

    @Override
    public User addUser(UserReq userReq) {
        User user = userMapper.fromUserReq(userReq);
        return userRepo.save(user);
    }

    @Override
    public User updateUser(long id, UserReq userReq) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        existingUser.setNom(userReq.getNom());
        existingUser.setEmail(userReq.getEmail());

        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(long id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        userRepo.delete(existingUser);
    }
}
