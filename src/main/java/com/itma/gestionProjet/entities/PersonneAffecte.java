package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonneAffecte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idPap;
    private String idParcelle;
    private Integer nombreParcelle;
    private String categorie;
    @NotBlank
    private String prenom;
    @NotBlank
    private String nom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String sexe;
    private  String pays;
    private Integer age;
    private String nationalite;
    @NotBlank
    private  String codePap;
    private String departement;
    private  String typeIdentification;
    private String numeroIdentification;
    private String numeroTelephone;
    private String localiteResidence;
    private String region;
    private String prenomMere;
    private String prenomPere;
    private String statutPap;
    private String statutVulnerable;
    private  String situationMatrimoniale;
    private String prenomExploitant;
    private String nomExploitant;
    private Double superficieAffecte;
    private String typeCulture;
    private String typeEquipement;
    private Double superficieCultive;
    private String descriptionBienAffecte;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    private String signaturePath;
    private  String imagePath;
}
