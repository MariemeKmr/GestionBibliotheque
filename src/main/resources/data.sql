-- ============================================================
--  Donnees de demonstration (optionnel)
--  Pour les charger : voir le README (section "Donnees de demo").
-- ============================================================

INSERT INTO livres (titre, auteur, isbn, annee_publication, categorie, quantite_totale, quantite_disponible) VALUES
('Une si longue lettre', 'Mariama Ba', '978-2708704305', 1979, 'Roman', 3, 3),
('L''Aventure ambigue', 'Cheikh Hamidou Kane', '978-2264031655', 1961, 'Roman', 2, 2),
('Les Bouts de bois de Dieu', 'Sembene Ousmane', '978-2266154574', 1960, 'Roman', 2, 2),
('Le Ventre de l''Atlantique', 'Fatou Diome', '978-2253112853', 2003, 'Roman', 4, 4),
('Clean Code', 'Robert C. Martin', '978-0132350884', 2008, 'Informatique', 2, 2),
('Spring in Action', 'Craig Walls', '978-1617294945', 2018, 'Informatique', 1, 1);

INSERT INTO utilisateurs (nom, prenom, email, telephone) VALUES
('Kamara', 'Marieme', 'marieme.kamara@example.sn', '+221 77 000 00 00'),
('Diop', 'Awa', 'awa.diop@example.sn', '+221 76 111 11 11'),
('Ndiaye', 'Moussa', 'moussa.ndiaye@example.sn', '+221 78 222 22 22');
