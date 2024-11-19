package com.mundia.msdocuments.services;

import com.mundia.msdocuments.dto.DocumentDTO;
import com.mundia.msdocuments.dto.DocumentReq;
import com.mundia.msdocuments.entities.Document;

import java.util.List;

public interface DocumentService {
    List<DocumentDTO> getAllDocuments();
    DocumentDTO getDocumentById(long id);
    List<DocumentDTO> getDocumentByTitle(String name);
    List<DocumentDTO> getDocumentsByProprietaireId(Long proprietaireId);
    Document addDocument(DocumentReq document);
    Document updateDocument(long id,DocumentReq document);
    void deleteDocument(long id);
}
