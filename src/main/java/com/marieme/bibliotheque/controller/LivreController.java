package com.marieme.bibliotheque.controller;

import com.marieme.bibliotheque.model.Livre;
import com.marieme.bibliotheque.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    /** Liste de tous les livres. */
    @GetMapping
    public String liste(Model model) {
        model.addAttribute("livres", livreService.listerTous());
        model.addAttribute("page", "livres");
        return "livres/liste";
    }

    /** Formulaire d'ajout. */
    @GetMapping("/nouveau")
    public String formulaireAjout(Model model) {
        model.addAttribute("livre", new Livre());
        model.addAttribute("page", "livres");
        return "livres/formulaire";
    }

    /** Formulaire de modification. */
    @GetMapping("/{id}/modifier")
    public String formulaireModification(@PathVariable Long id, Model model) {
        model.addAttribute("livre", livreService.trouverParId(id));
        model.addAttribute("page", "livres");
        return "livres/formulaire";
    }

    /** Enregistrement (ajout + modification). */
    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute("livre") Livre livre,
                              BindingResult resultat,
                              Model model,
                              RedirectAttributes redirect) {
        if (resultat.hasErrors()) {
            model.addAttribute("page", "livres");
            return "livres/formulaire";
        }
        livreService.enregistrer(livre);
        redirect.addFlashAttribute("succes", "Livre enregistre avec succes.");
        return "redirect:/livres";
    }

    /** Suppression. */
    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            livreService.supprimer(id);
            redirect.addFlashAttribute("succes", "Livre supprime.");
        } catch (Exception e) {
            redirect.addFlashAttribute("erreur",
                    "Impossible de supprimer ce livre : il est lie a des emprunts.");
        }
        return "redirect:/livres";
    }
}
