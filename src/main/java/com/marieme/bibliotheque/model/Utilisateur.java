package com.marieme.bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represente un utilisateur (adherent) de la bibliotheque.
 */
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @Column(nullable = false)
    private String prenom;

    @Email(message = "Adresse email invalide")
    @NotBlank(message = "L'email est obligatoire")
    @Column(nullable = false, unique = true)
    private String email;

    private String telephone;

    public Utilisateur() {
    }

    @Transient
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
