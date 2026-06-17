package com.marieme.bibliotheque.service;

import com.marieme.bibliotheque.model.Livre;
import com.marieme.bibliotheque.repository.LivreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<Livre> listerTous() {
        return livreRepository.findAll();
    }

    public List<Livre> listerDisponibles() {
        return livreRepository.findByQuantiteDisponibleGreaterThan(0);
    }

    public Livre trouverParId(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable (id=" + id + ")"));
    }

    @Transactional
    public Livre enregistrer(Livre livre) {
        // A la creation, la quantite disponible suit la quantite totale.
        if (livre.getId() == null) {
            livre.setQuantiteDisponible(livre.getQuantiteTotale());
        } else {
            // En modification, on ajuste le stock disponible selon l'ecart de stock total.
            Livre existant = trouverParId(livre.getId());
            int empruntesEnCours = existant.getQuantiteTotale() - existant.getQuantiteDisponible();
            int nouvelleDispo = livre.getQuantiteTotale() - empruntesEnCours;
            livre.setQuantiteDisponible(Math.max(0, nouvelleDispo));
        }
        return livreRepository.save(livre);
    }

    @Transactional
    public void supprimer(Long id) {
        livreRepository.deleteById(id);
    }

    public long compter() {
        return livreRepository.count();
    }
}
