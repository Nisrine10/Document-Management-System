package com.mundia.msdocuments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentReq {
    private String titre;
    private String contenu;
    private Long proprietaireId;
}
