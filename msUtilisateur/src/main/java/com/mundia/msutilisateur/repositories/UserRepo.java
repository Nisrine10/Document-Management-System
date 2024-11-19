package com.mundia.msutilisateur.repositories;

import com.mundia.msutilisateur.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findAllByNom(String nom);

}
