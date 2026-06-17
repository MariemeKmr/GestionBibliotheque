package com.marieme.bibliotheque.service;

import com.marieme.bibliotheque.model.Emprunt;
import com.marieme.bibliotheque.model.Livre;
import com.marieme.bibliotheque.model.StatutEmprunt;
import com.marieme.bibliotheque.model.Utilisateur;
import com.marieme.bibliotheque.repository.EmpruntRepository;
import com.marieme.bibliotheque.repository.LivreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    /** Duree d'un pret en jours. */
    private static final int DUREE_PRET_JOURS = 14;

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;
    private final LivreService livreService;
    private final UtilisateurService utilisateurService;

    public EmpruntService(EmpruntRepository empruntRepository,
                          LivreRepository livreRepository,
                          LivreService livreService,
                          UtilisateurService utilisateurService) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
        this.livreService = livreService;
        this.utilisateurService = utilisateurService;
    }

    public List<Emprunt> historique() {
        return empruntRepository.findAllByOrderByDateEmpruntDescIdDesc();
    }

    public List<Emprunt> empruntsEnCours() {
        return empruntRepository.findByStatutOrderByDateEmpruntDesc(StatutEmprunt.EN_COURS);
    }

    public long compterEnCours() {
        return empruntRepository.countByStatut(StatutEmprunt.EN_COURS);
    }

    /**
     * Enregistre un nouvel emprunt et decremente le stock disponible du livre.
     */
    @Transactional
    public Emprunt emprunter(Long livreId, Long utilisateurId) {
        Livre livre = livreService.trouverParId(livreId);
        Utilisateur utilisateur = utilisateurService.trouverParId(utilisateurId);

        if (!livre.isDisponible()) {
            throw new IllegalStateException("Le livre \"" + livre.getTitre() + "\" n'est pas disponible.");
        }

        // Decrementation du stock
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);

        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(livre);
        emprunt.setUtilisateur(utilisateur);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(DUREE_PRET_JOURS));
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        return empruntRepository.save(emprunt);
    }

    /**
     * Marque un emprunt comme retourne et reincremente le stock disponible.
     */
    @Transactional
    public Emprunt retourner(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable (id=" + empruntId + ")"));

        if (emprunt.getStatut() == StatutEmprunt.RETOURNE) {
            throw new IllegalStateException("Cet emprunt a deja ete retourne.");
        }

        emprunt.setStatut(StatutEmprunt.RETOURNE);
        emprunt.setDateRetourEffective(LocalDate.now());
        empruntRepository.save(emprunt);

        // Reincrementation du stock
        Livre livre = emprunt.getLivre();
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreRepository.save(livre);

        return emprunt;
    }
}
