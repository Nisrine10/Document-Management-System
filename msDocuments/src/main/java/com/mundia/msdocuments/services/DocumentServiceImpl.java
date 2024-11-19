package com.mundia.msdocuments.services;

import com.mundia.msdocuments.dto.DocumentDTO;
import com.mundia.msdocuments.dto.DocumentReq;
import com.mundia.msdocuments.dto.UserDTO;
import com.mundia.msdocuments.entities.Document;
import com.mundia.msdocuments.mappers.DocumentMapper;
import com.mundia.msdocuments.repositories.DocumentRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    final DocumentRepo documentRepo;
    final DocumentMapper documentMapper;
     final WebClient webClient;
//    final WebClient webClient;

    @Override
    public List<DocumentDTO> getAllDocuments() {

        List<Document> documents = documentRepo.findAll();

        // Map Partage to PartageDTO and fetch related data using WebClient
        return documents.stream().map(document -> {

            UserDTO user = webClient.get()
                    .uri("http://MSUTILISATEUR/api/users/" + document.getProprietaireId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block(); // Blocking call

            System.out.println(user);
            // Map to PartageDTO
            DocumentDTO documentDTO = new DocumentDTO();
            BeanUtils.copyProperties(document, documentDTO);
            documentDTO.setProprietaire(user);


            return documentDTO ;
        }).toList();
    }

    @Override
    public DocumentDTO getDocumentById(long id) {


        Optional<Document> optionalDocument = documentRepo.findById(id);
        if(optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            UserDTO user = webClient.get()
                    .uri("http://MSUTILISATEUR/api/users/" + document.getProprietaireId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block(); // Blocking call

            System.out.println(user);
            // Map to PartageDTO
            DocumentDTO documentDTO = new DocumentDTO();
            BeanUtils.copyProperties(document, documentDTO);
            documentDTO.setProprietaire(user);

            return documentDTO;
        }else{
            throw new EntityNotFoundException("Document with id "+id+" not found");

        }


    }

    @Override
    public List<DocumentDTO> getDocumentByTitle(String name) {
       // return documentRepo.findAllByTitre(name);

        List<Document> documents = documentRepo.findAllByTitre(name);

        // Map Partage to PartageDTO and fetch related data using WebClient
        return documents.stream().map(document -> {

            UserDTO user = webClient.get()
                    .uri("http://MSUTILISATEUR/api/users/" + document.getProprietaireId())
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block(); // Blocking call

            System.out.println(user);
            // Map to PartageDTO
            DocumentDTO documentDTO = new DocumentDTO();
            BeanUtils.copyProperties(document, documentDTO);
            documentDTO.setProprietaire(user);


            return documentDTO ;
        }).toList();
    }

    @Override
    public List<DocumentDTO> getDocumentsByProprietaireId(Long proprietaireId) {
        // Fetch documents by proprietaireId
        List<Document> documents = documentRepo.findByProprietaireId(proprietaireId);

        // Convert entities to DTOs and return
        return documents.stream().map(document -> {
            DocumentDTO documentDTO = new DocumentDTO();
            BeanUtils.copyProperties(document, documentDTO);
            System.out.println(documentDTO);
            return documentDTO;
        }).toList();
    }
    @Override
    public Document addDocument(DocumentReq document) {
        Document document1 = documentMapper.fromDocumentReq(document);
        documentRepo.save(document1);
        return document1;
    }

    @Override
    public Document updateDocument(long id, DocumentReq documentReq) {
        Document existingDocument = documentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id " + id + " not found"));

        existingDocument.setTitre(documentReq.getTitre());
        existingDocument.setContenu(documentReq.getContenu());
        existingDocument.setProprietaireId(documentReq.getProprietaireId());

        return documentRepo.save(existingDocument);
    }


    @Override
    public void deleteDocument(long id) {
        Document existingDocument = documentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id " + id + " not found"));

        documentRepo.delete(existingDocument);
    }
}
