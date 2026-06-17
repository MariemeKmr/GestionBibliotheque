package com.marieme.bibliotheque.controller;

import com.marieme.bibliotheque.service.EmpruntService;
import com.marieme.bibliotheque.service.LivreService;
import com.marieme.bibliotheque.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;
    private final LivreService livreService;
    private final UtilisateurService utilisateurService;

    public EmpruntController(EmpruntService empruntService,
                             LivreService livreService,
                             UtilisateurService utilisateurService) {
        this.empruntService = empruntService;
        this.livreService = livreService;
        this.utilisateurService = utilisateurService;
    }

    /** Historique complet des emprunts. */
    @GetMapping
    public String historique(Model model) {
        model.addAttribute("emprunts", empruntService.historique());
        model.addAttribute("page", "emprunts");
        return "emprunts/liste";
    }

    /** Formulaire pour realiser un nouvel emprunt. */
    @GetMapping("/nouveau")
    public String formulaireEmprunt(Model model) {
        model.addAttribute("livresDisponibles", livreService.listerDisponibles());
        model.addAttribute("utilisateurs", utilisateurService.listerTous());
        model.addAttribute("page", "emprunts");
        return "emprunts/nouveau";
    }

    /** Enregistrement d'un emprunt. */
    @PostMapping("/emprunter")
    public String emprunter(@RequestParam Long livreId,
                            @RequestParam Long utilisateurId,
                            RedirectAttributes redirect) {
        try {
            empruntService.emprunter(livreId, utilisateurId);
            redirect.addFlashAttribute("succes", "Emprunt enregistre avec succes.");
        } catch (Exception e) {
            redirect.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/emprunts/nouveau";
        }
        return "redirect:/emprunts";
    }

    /** Retour d'un livre. */
    @PostMapping("/{id}/retourner")
    public String retourner(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            empruntService.retourner(id);
            redirect.addFlashAttribute("succes", "Livre retourne avec succes.");
        } catch (Exception e) {
            redirect.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/emprunts";
    }
}
