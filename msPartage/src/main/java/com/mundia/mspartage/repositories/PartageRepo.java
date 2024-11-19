package com.mundia.mspartage.repositories;

import com.mundia.mspartage.entities.Partage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PartageRepo extends JpaRepository<Partage, Long> {
    List<Partage> findByDocumentId(Long documentId);
    List<Partage> findByUtilisateurId(Long utilisateurId);
}
