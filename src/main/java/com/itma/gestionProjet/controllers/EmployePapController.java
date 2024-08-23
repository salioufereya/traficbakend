package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EmployePapDTO;
import com.itma.gestionProjet.entities.EmployePap;
import com.itma.gestionProjet.requests.EmployePapRequest;
import com.itma.gestionProjet.services.EmployePapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/employePap")
public class EmployePapController {

    @Autowired
    private EmployePapService employePapService;

    @GetMapping
    public ResponseEntity<AApiResponse<EmployePap>> getAllEmployePaps(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {
        Pageable pageable = PageRequest.of(offset, max);
        AApiResponse<EmployePap> response = employePapService.getAllEmployePaps(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AApiResponse<List<EmployePap>>> createEmployePap(@RequestBody List<EmployePapRequest> employePapRequests) {
        AApiResponse<List<EmployePap>> response = new AApiResponse<>();
        try {
            List<EmployePap> employePaps = employePapService.createEmployePap(employePapRequests);
            response.setResponseCode(HttpStatus.CREATED.value());
            response.setData(Collections.singletonList(employePaps)); // Pas besoin d'encapsuler dans une autre liste
            response.setMessage("Employés PAP créés avec succès");
            response.setLength(employePaps.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Erreur lors de la création des employés PAP : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployePap> updateEmployePap(@PathVariable Long id, @RequestBody EmployePap employePap) {
        EmployePap updatedEmployePap = employePapService.updateEmployePap(id, employePap);
        return new ResponseEntity<>(updatedEmployePap, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployePap(@PathVariable Long id) {
        employePapService.deleteEmployePap(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<List<EmployePap>>> getEmployesByCodePap(@RequestParam String codePap,
                                                                               @RequestParam(defaultValue = "0") int offset,
                                                                               @RequestParam(defaultValue = "100") int max) {
        AApiResponse<List<EmployePap>> response = employePapService.getEmployesByCodePap(codePap, offset, max);

        if (response.getData() == null) {
            return ResponseEntity.status(response.getResponseCode()).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
