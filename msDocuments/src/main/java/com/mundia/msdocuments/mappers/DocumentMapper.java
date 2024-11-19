package com.mundia.msdocuments.mappers;

import com.mundia.msdocuments.dto.DocumentReq;
import com.mundia.msdocuments.entities.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DocumentMapper {
    public Document fromDocumentReq(DocumentReq docuemntReq){
        Document document = new Document();
        BeanUtils.copyProperties(docuemntReq, document);
        return document;

    }
}
