package com.marieme.bibliotheque.service;

import com.marieme.bibliotheque.model.Utilisateur;
import com.marieme.bibliotheque.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> listerTous() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur trouverParId(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable (id=" + id + ")"));
    }

    @Transactional
    public Utilisateur enregistrer(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public boolean emailExiste(String email) {
        return utilisateurRepository.existsByEmail(email);
    }

    public long compter() {
        return utilisateurRepository.count();
    }
}
