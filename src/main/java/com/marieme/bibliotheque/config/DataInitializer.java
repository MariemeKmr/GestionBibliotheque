package com.marieme.bibliotheque.config;

import com.marieme.bibliotheque.model.Emprunt;
import com.marieme.bibliotheque.model.Livre;
import com.marieme.bibliotheque.model.StatutEmprunt;
import com.marieme.bibliotheque.model.Utilisateur;
import com.marieme.bibliotheque.repository.EmpruntRepository;
import com.marieme.bibliotheque.repository.LivreRepository;
import com.marieme.bibliotheque.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Initialise la base avec des donnees de demonstration.
 * Les donnees ne sont inserees QUE si la base est vide, donc :
 *  - elles persistent ensuite (rien n'est efface au redemarrage) ;
 *  - elles ne se dupliquent jamais ;
 *  - en production, la base est peuplee automatiquement au premier lancement.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final LivreRepository livreRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmpruntRepository empruntRepository;

    public DataInitializer(LivreRepository livreRepository,
                           UtilisateurRepository utilisateurRepository,
                           EmpruntRepository empruntRepository) {
        this.livreRepository = livreRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.empruntRepository = empruntRepository;
    }

    @Override
    public void run(String... args) {
        // Si des donnees existent deja, on ne fait rien (evite les doublons).
        if (livreRepository.count() > 0 || utilisateurRepository.count() > 0) {
            return;
        }

        // ---------------- Livres ----------------
        Livre l1 = creerLivre("Une si longue lettre", "Mariama Ba", "978-2708704305", 1979, "Roman", 3);
        Livre l2 = creerLivre("L'Aventure ambigue", "Cheikh Hamidou Kane", "978-2264031655", 1961, "Roman", 2);
        Livre l3 = creerLivre("Les Bouts de bois de Dieu", "Sembene Ousmane", "978-2266154574", 1960, "Roman", 2);
        Livre l4 = creerLivre("Le Ventre de l'Atlantique", "Fatou Diome", "978-2253112853", 2003, "Roman", 4);
        Livre l5 = creerLivre("Clean Code", "Robert C. Martin", "978-0132350884", 2008, "Informatique", 2);
        creerLivre("Spring in Action", "Craig Walls", "978-1617294945", 2018, "Informatique", 1);

        // ---------------- Utilisateurs ----------------
        Utilisateur u1 = creerUtilisateur("Kamara", "Marieme", "marieme.kamara@example.sn", "+221 77 000 00 00");
        Utilisateur u2 = creerUtilisateur("Diop", "Awa", "awa.diop@example.sn", "+221 76 111 11 11");
        Utilisateur u3 = creerUtilisateur("Ndiaye", "Moussa", "moussa.ndiaye@example.sn", "+221 78 222 22 22");

        // ---------------- Emprunts (pour avoir un historique) ----------------

        // Emprunt en cours (il y a 5 jours, retour prevu dans 9 jours)
        creerEmpruntEnCours(l1, u2, LocalDate.now().minusDays(5));

        // Emprunt en cours et EN RETARD (retour prevu deja depasse)
        creerEmpruntEnCours(l5, u3, LocalDate.now().minusDays(20));

        // Emprunt deja retourne (historique)
        creerEmpruntRetourne(l3, u1,
                LocalDate.now().minusDays(30),   // date d'emprunt
                LocalDate.now().minusDays(18));  // date de retour effective
    }

    // ---------------- Methodes utilitaires ----------------

    private Livre creerLivre(String titre, String auteur, String isbn,
                             Integer annee, String categorie, int quantite) {
        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setIsbn(isbn);
        livre.setAnneePublication(annee);
        livre.setCategorie(categorie);
        livre.setQuantiteTotale(quantite);
        livre.setQuantiteDisponible(quantite);
        return livreRepository.save(livre);
    }

    private Utilisateur creerUtilisateur(String nom, String prenom, String email, String telephone) {
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setEmail(email);
        u.setTelephone(telephone);
        return utilisateurRepository.save(u);
    }

    private void creerEmpruntEnCours(Livre livre, Utilisateur utilisateur, LocalDate dateEmprunt) {
        Emprunt e = new Emprunt();
        e.setLivre(livre);
        e.setUtilisateur(utilisateur);
        e.setDateEmprunt(dateEmprunt);
        e.setDateRetourPrevue(dateEmprunt.plusDays(14));
        e.setStatut(StatutEmprunt.EN_COURS);
        empruntRepository.save(e);

        // On retire un exemplaire du stock disponible.
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);
    }

    private void creerEmpruntRetourne(Livre livre, Utilisateur utilisateur,
                                      LocalDate dateEmprunt, LocalDate dateRetour) {
        Emprunt e = new Emprunt();
        e.setLivre(livre);
        e.setUtilisateur(utilisateur);
        e.setDateEmprunt(dateEmprunt);
        e.setDateRetourPrevue(dateEmprunt.plusDays(14));
        e.setDateRetourEffective(dateRetour);
        e.setStatut(StatutEmprunt.RETOURNE);
        empruntRepository.save(e);
        // Le livre a ete rendu : le stock disponible reste inchange.
    }
}
