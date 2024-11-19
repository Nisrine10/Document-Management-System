package com.mundia.msdocuments.web;

import com.mundia.msdocuments.dto.DocumentDTO;
import com.mundia.msdocuments.dto.DocumentReq;
import com.mundia.msdocuments.entities.Document;
import com.mundia.msdocuments.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    final DocumentService documentService;

    // Add a new document
    @PostMapping("/add")
    public Document addDocument(@RequestBody DocumentReq documentReq) {
        return documentService.addDocument(documentReq);
    }

    // pour avoir les documents par proprietaire
    @GetMapping("/proprietaire/{proprietaireId}")
    public List<DocumentDTO> getDocumentsByProprietaireId(@PathVariable Long proprietaireId) {

        return documentService.getDocumentsByProprietaireId(proprietaireId);
    }

    // Get a document by ID
    @GetMapping("/{id}")
    public DocumentDTO getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    // Get all documents
    @GetMapping("/all")
    public List<DocumentDTO> getAllDocuments() {

        return documentService.getAllDocuments();
    }

    // Get documents by title
    @GetMapping("/title/{title}")
    public List<DocumentDTO> getDocumentsByTitle(@PathVariable String title) {
        return documentService.getDocumentByTitle(title);
    }

    // Update a document
    @PutMapping("/update/{id}")
    public Document updateDocument(@PathVariable Long id, @RequestBody DocumentReq documentReq) {
        return documentService.updateDocument(id, documentReq);
    }

    // Delete a document
    @DeleteMapping("/delete/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }
}
