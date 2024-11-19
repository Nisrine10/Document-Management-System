package com.mundia.mspartage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartageDTO {
    private Long id;
    private DocumentDTO document;
    private UserDTO utilisateur;
}
