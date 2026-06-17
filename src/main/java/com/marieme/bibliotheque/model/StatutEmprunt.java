package com.marieme.bibliotheque.model;

/**
 * Statut d'un emprunt.
 */
public enum StatutEmprunt {
    EN_COURS("En cours"),
    RETOURNE("Retourne");

    private final String libelle;

    StatutEmprunt(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
