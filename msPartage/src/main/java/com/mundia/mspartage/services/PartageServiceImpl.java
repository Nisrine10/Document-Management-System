package com.mundia.mspartage.services;

import com.mundia.mspartage.dto.DocumentDTO;
import com.mundia.mspartage.dto.PartageDTO;
import com.mundia.mspartage.dto.PartageReq;
import com.mundia.mspartage.dto.UserDTO;
import com.mundia.mspartage.entities.Partage;
import com.mundia.mspartage.mappers.UserMapper;
import com.mundia.mspartage.repositories.PartageRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartageServiceImpl implements PartageService {
    private final PartageRepo partageRepo;
    final WebClient webClient;

    @Override
    public List<PartageDTO> getAllPartages() {
        List<Partage> partages = partageRepo.findAll();

        // Map Partage to PartageDTO and fetch related data using WebClient
        return partages.stream().map(partage -> {
            // Fetch DocumentDTO using WebClient
            DocumentDTO document = webClient.get()
                    .uri("http://MSDOCUMENTS/api/documents/" + partage.getDocumentId())
                    .retrieve()
                    .bodyToMono(DocumentDTO.class)
                    .block(); // Blocking call

            System.out.println(document);

            // Fetch UserDTO using WebClient
            UserDTO user = webClient.get()
                    .uri("http://MSUTILISATEUR/api/users/" + partage.getUtilisateurId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block(); // Blocking call

            System.out.println();
            // Map to PartageDTO
            PartageDTO partageDTO = new PartageDTO();
            BeanUtils.copyProperties(partage, partageDTO);
            partageDTO.setDocument(document);
            partageDTO.setUtilisateur(user);

            return partageDTO;
        }).toList();
    }

    @Override
    public List<Partage> getPartagesByDocumentId(Long documentId) {
        return partageRepo.findByDocumentId(documentId);
    }

    @Override
    public List<Partage> getPartagesByUtilisateurId(Long utilisateurId) {
        return partageRepo.findByUtilisateurId(utilisateurId);
    }

    public List<DocumentDTO> getDocumentsByUserId(Long userId) {
        // Fetch Partages for the user
        List<Partage> partages = partageRepo.findByUtilisateurId(userId);

        // Map to DocumentDTO by fetching document details using WebClient
        return partages.stream().map(partage -> {
            DocumentDTO document = webClient.get()
                    .uri("http://MSDOCUMENTS/api/documents/" + partage.getDocumentId())
                    .retrieve()
                    .bodyToMono(DocumentDTO.class)
                    .block(); // Fetch document details
            return document;
        }).toList();
    }

    @Override
    public Partage addPartage(Long documentId, Long utilisateurId) {
        if (documentId == null || utilisateurId == null) {
            throw new IllegalArgumentException("Document ID and Utilisateur ID cannot be null");
        }

        Partage partage = new Partage();
        partage.setDocumentId(documentId);
        partage.setUtilisateurId(utilisateurId);

        try {
            return partageRepo.save(partage);
        } catch (Exception e) {
            throw new RuntimeException("Error saving partage", e);
        }
    }

    @Override
    public void deletePartage(Long id) {
        Partage existingPartage = partageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partage with id " + id + " not found"));
        partageRepo.delete(existingPartage);
    }
}
