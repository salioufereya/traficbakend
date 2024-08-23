package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.ApiResponse;
import com.itma.gestionProjet.dtos.LettreConsentementDTO;
import com.itma.gestionProjet.entities.LettreConsentement;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.exceptions.UserNotFoundException;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.services.LettreConsentementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lettres_consentement")
public class LettreConsentementController {
    @Autowired
    private LettreConsentementService lettreConsentementService;

    @Autowired
    private  PersonneAffecteRepository personneAffecteRepository;
    @GetMapping
    public List<LettreConsentement> getAllLettresConsentement() {
        return lettreConsentementService.getAllLettresConsentement();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LettreConsentement> getLettreConsentementById(@PathVariable Long id) {
        LettreConsentement lettreConsentement = lettreConsentementService.getLettreConsentementById(id);
        if (lettreConsentement != null) {
            return ResponseEntity.ok(lettreConsentement);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createLettreConsentement(@RequestBody LettreConsentementDTO lettreConsentementDTO) {
        try {
            PersonneAffecte personneAffecte = personneAffecteRepository.findById(lettreConsentementDTO.getPartie_affecte_id())
                    .orElseThrow(() -> new UserNotFoundException("PersonneAffecte not found"));

            LettreConsentement lettreConsentement = new LettreConsentement();
            lettreConsentement.setContenu(lettreConsentementDTO.getContenu());
            lettreConsentement.setPersonneAffecte(personneAffecte);
            LettreConsentement createdLettreConsentement = lettreConsentementService.createLettreConsentement(lettreConsentement);
            ApiResponse<LettreConsentement> response = new ApiResponse<>(200, "Lettre de consentement créée avec succès", createdLettreConsentement);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException ex) {
            ApiResponse<String> errorResponse = new ApiResponse<>(404, ex.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<String> errorResponse = new ApiResponse<>(500, "Erreur lors de la création de la lettre de consentement" +ex.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LettreConsentement> updateLettreConsentement(@PathVariable Long id, @RequestBody LettreConsentement lettreConsentement) {
        LettreConsentement updatedLettre = lettreConsentementService.updateLettreConsentement(id, lettreConsentement);
        if (updatedLettre != null) {
            return ResponseEntity.ok(updatedLettre);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLettreConsentement(@PathVariable Long id) {
        lettreConsentementService.deleteLettreConsentement(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/personne_affecte/{id}")
    public ResponseEntity<ApiResponse<LettreConsentement>> getLettreConsentementByPersonneAffecte(@PathVariable Long id) {
        PersonneAffecte personneAffecte = new PersonneAffecte();
        personneAffecte.setId(id);
        LettreConsentement lettreConsentement = lettreConsentementService.getLettreConsentementByPersonneAffecte(personneAffecte);
        if (lettreConsentement != null) {
            ApiResponse<LettreConsentement> response = new ApiResponse<>(HttpStatus.OK.value(), "Lettre de consentement trouvée", lettreConsentement);
            return ResponseEntity.ok(response);
        }
        ApiResponse<LettreConsentement> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Lettre de consentement non trouvée", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
