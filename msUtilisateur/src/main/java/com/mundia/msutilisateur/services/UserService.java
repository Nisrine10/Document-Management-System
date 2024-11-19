package com.mundia.msutilisateur.services;

import com.mundia.msutilisateur.dto.UserReq;
import com.mundia.msutilisateur.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    List<User> getUsersByName(String name);
    User addUser(UserReq userReq);
    User updateUser(long id, UserReq userReq);
    void deleteUser(long id);
}