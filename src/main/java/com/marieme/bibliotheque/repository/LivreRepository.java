package com.marieme.bibliotheque.repository;

import com.marieme.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

    /** Livres ayant au moins un exemplaire disponible. */
    List<Livre> findByQuantiteDisponibleGreaterThan(int quantite);

    /** Recherche par titre ou auteur (insensible a la casse). */
    List<Livre> findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(String titre, String auteur);
}
