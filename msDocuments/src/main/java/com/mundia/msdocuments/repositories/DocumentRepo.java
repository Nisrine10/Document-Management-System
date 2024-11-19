package com.mundia.msdocuments.repositories;

import com.mundia.msdocuments.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DocumentRepo extends JpaRepository<Document, Long> {
    List<Document> findAllByTitre(String nom);
    List<Document> findByProprietaireId(Long proprietaireId);

}
