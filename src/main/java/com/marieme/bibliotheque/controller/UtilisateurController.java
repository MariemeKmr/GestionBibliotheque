package com.marieme.bibliotheque.controller;

import com.marieme.bibliotheque.model.Utilisateur;
import com.marieme.bibliotheque.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /** Liste des utilisateurs. */
    @GetMapping
    public String liste(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.listerTous());
        model.addAttribute("page", "utilisateurs");
        return "utilisateurs/liste";
    }

    /** Formulaire d'ajout. */
    @GetMapping("/nouveau")
    public String formulaireAjout(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("page", "utilisateurs");
        return "utilisateurs/formulaire";
    }

    /** Enregistrement. */
    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
                              BindingResult resultat,
                              Model model,
                              RedirectAttributes redirect) {
        // Verification de l'unicite de l'email a la creation
        if (utilisateur.getId() == null
                && utilisateur.getEmail() != null
                && utilisateurService.emailExiste(utilisateur.getEmail())) {
            resultat.rejectValue("email", "email.existe", "Cet email est deja utilise.");
        }
        if (resultat.hasErrors()) {
            model.addAttribute("page", "utilisateurs");
            return "utilisateurs/formulaire";
        }
        utilisateurService.enregistrer(utilisateur);
        redirect.addFlashAttribute("succes", "Utilisateur enregistre avec succes.");
        return "redirect:/utilisateurs";
    }
}
