package com.itma.gestionProjet.dtos;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class TacheDTO {
    private Long id;
    private String libelle;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String statut;
    private Set<ConsultantResponse> utilisateurs    ;
}
