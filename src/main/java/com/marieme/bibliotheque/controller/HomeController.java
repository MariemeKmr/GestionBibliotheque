package com.marieme.bibliotheque.controller;

import com.marieme.bibliotheque.service.EmpruntService;
import com.marieme.bibliotheque.service.LivreService;
import com.marieme.bibliotheque.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final LivreService livreService;
    private final UtilisateurService utilisateurService;
    private final EmpruntService empruntService;

    public HomeController(LivreService livreService,
                          UtilisateurService utilisateurService,
                          EmpruntService empruntService) {
        this.livreService = livreService;
        this.utilisateurService = utilisateurService;
        this.empruntService = empruntService;
    }

    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("nbLivres", livreService.compter());
        model.addAttribute("nbUtilisateurs", utilisateurService.compter());
        model.addAttribute("nbEmpruntsEnCours", empruntService.compterEnCours());
        model.addAttribute("page", "accueil");
        return "index";
    }
}
