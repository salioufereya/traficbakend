package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.PersonneAffecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneAffecteRepository extends JpaRepository<PersonneAffecte, Long> {
}