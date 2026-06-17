package com.marieme.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represente l'emprunt d'un livre par un utilisateur.
 */
@Entity
@Table(name = "emprunts")
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "date_emprunt", nullable = false)
    private LocalDate dateEmprunt;

    @Column(name = "date_retour_prevue")
    private LocalDate dateRetourPrevue;

    @Column(name = "date_retour_effective")
    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEmprunt statut = StatutEmprunt.EN_COURS;

    public Emprunt() {
    }

    /** Vrai si la date de retour prevue est depassee et le livre non rendu. */
    @Transient
    public boolean isEnRetard() {
        return statut == StatutEmprunt.EN_COURS
                && dateRetourPrevue != null
                && dateRetourPrevue.isBefore(LocalDate.now());
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public StatutEmprunt getStatut() {
        return statut;
    }

    public void setStatut(StatutEmprunt statut) {
        this.statut = statut;
    }
}
