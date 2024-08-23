package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BetailsDTO;
import com.itma.gestionProjet.entities.Betails;
import com.itma.gestionProjet.requests.BetailsRequest;
import com.itma.gestionProjet.services.BetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/betails")
public class BetailsController {

    @Autowired
    private BetailsService betailsService;

    @PostMapping()
    public ResponseEntity<AApiResponse<List<BetailsDTO>>> createBetails(@RequestBody List<BetailsRequest> requests) {
        AApiResponse<List<BetailsDTO>> response = new AApiResponse<>();
        try {
            List<BetailsDTO> betailsDTOs = (List<BetailsDTO>) betailsService.createBetails(requests);
            response.setResponseCode(HttpStatus.CREATED.value());
            response.setData(Collections.singletonList(betailsDTOs));
            response.setMessage("Betails créés avec succès");
            response.setLength(betailsDTOs.size());
            response.setOffset(0);
            response.setMax(requests.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Erreur lors de la création des Betails : " + e.getMessage());
            response.setData(null);
            response.setLength(0);
            response.setOffset(0);
            response.setMax(0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping
    public AApiResponse<Betails> getAllBetails(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return betailsService.getAllBetails(page, size);
    }

    @GetMapping("/{id}")
    public AApiResponse<Betails> getBetailsById(@PathVariable Long id) {
        return betailsService.getBetailsById(id);
    }

    @PutMapping("/{id}")
    public AApiResponse<Betails> updateBetails(@PathVariable Long id, @RequestBody Betails betails) {
        return betailsService.updateBetails(id, betails);
    }

    @DeleteMapping("/{id}")
    public AApiResponse<Betails> deleteBetails(@PathVariable Long id) {
        return betailsService.deleteBetails(id);
    }




    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<List<Betails>>> getBetailsByCodePap(@RequestParam String codePap,
                                                                           @RequestParam(defaultValue = "0") int offset,
                                                                           @RequestParam(defaultValue = "10") int max) {
        AApiResponse<List<Betails>> response = betailsService.getBetailsByCodePap(codePap, offset, max);

        if (response.getData() == null) {
            return ResponseEntity.status(response.getResponseCode()).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
