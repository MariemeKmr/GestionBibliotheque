package com.marieme.bibliotheque.repository;

import com.marieme.bibliotheque.model.Emprunt;
import com.marieme.bibliotheque.model.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    /** Historique complet, du plus recent au plus ancien. */
    List<Emprunt> findAllByOrderByDateEmpruntDescIdDesc();

    /** Emprunts filtres par statut (EN_COURS / RETOURNE). */
    List<Emprunt> findByStatutOrderByDateEmpruntDesc(StatutEmprunt statut);

    long countByStatut(StatutEmprunt statut);
}
