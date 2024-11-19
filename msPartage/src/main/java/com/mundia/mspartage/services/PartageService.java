package com.mundia.mspartage.services;

import com.mundia.mspartage.dto.DocumentDTO;
import com.mundia.mspartage.dto.PartageDTO;
import com.mundia.mspartage.dto.PartageReq;
import com.mundia.mspartage.entities.Partage;

import java.util.List;

public interface PartageService {
    List<PartageDTO> getAllPartages();
    List<DocumentDTO> getDocumentsByUserId(Long userId);
    List<Partage> getPartagesByDocumentId(Long documentId);
    List<Partage> getPartagesByUtilisateurId(Long utilisateurId);
    Partage addPartage(Long documentId, Long utilisateurId);
    void deletePartage(Long id);
}