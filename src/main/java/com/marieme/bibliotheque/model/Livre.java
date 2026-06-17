package com.marieme.bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represente un livre du catalogue de la bibliotheque.
 */
@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "L'auteur est obligatoire")
    @Column(nullable = false)
    private String auteur;

    private String isbn;

    @Min(value = 0, message = "L'annee doit etre positive")
    @Column(name = "annee_publication")
    private Integer anneePublication;

    private String categorie;

    @NotNull(message = "La quantite totale est obligatoire")
    @Min(value = 1, message = "Il faut au moins 1 exemplaire")
    @Column(name = "quantite_totale", nullable = false)
    private Integer quantiteTotale = 1;

    @Column(name = "quantite_disponible", nullable = false)
    private Integer quantiteDisponible = 1;

    public Livre() {
    }

    /** Vrai si au moins un exemplaire est disponible a l'emprunt. */
    @Transient
    public boolean isDisponible() {
        return quantiteDisponible != null && quantiteDisponible > 0;
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getQuantiteTotale() {
        return quantiteTotale;
    }

    public void setQuantiteTotale(Integer quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }

    public Integer getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(Integer quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }
}
