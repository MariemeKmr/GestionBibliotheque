# Gestion de Bibliothèque

Application web de gestion d'une bibliothèque (livres, utilisateurs, emprunts) construite avec **Spring Boot** et **Thymeleaf**.

**Auteur :** Marième Kamara
**Stack :** Java 17 · Spring Boot 3 · Thymeleaf · Spring Data JPA · MySQL

---

## Fonctionnalités

### Livres
- Ajouter, modifier, supprimer un livre
- Lister tous les livres
- Suivi de la disponibilité (nombre d'exemplaires disponibles / total)
- Statut visuel : *Disponible* / *Emprunté*

### Utilisateurs
- Ajouter un utilisateur (adhérent)
- Lister les utilisateurs
- Email unique (vérifié automatiquement)

### Emprunts
- Emprunter un livre (décrémente le stock automatiquement)
- Retourner un livre (réincrémente le stock)
- Historique complet des emprunts
- Date de retour prévue fixée à **14 jours** ; détection des emprunts **en retard**

### Interface
- Design simple, clair et minimaliste
- Palette : marron · beige clair · orange doux
- Icônes SVG (aucune dépendance externe), coins arrondis

---

## Prérequis

- **Java 17** ou supérieur
- **Maven 3.8+** (ou utilisez le wrapper si vous l'ajoutez)
- **MySQL** (via XAMPP, MAMP, ou installation locale)

Vérifiez vos versions :

```bash
java -version
mvn -version
```

---

## Installation et lancement

### 1. Démarrer MySQL
Lancez MySQL (par exemple via le panneau **XAMPP**). La base de données `bibliotheque_db` sera **créée automatiquement** au premier démarrage de l'application.

### 2. Configurer la connexion
Ouvrez `src/main/resources/application.properties` et adaptez si besoin.


### 3. Lancer l'application

```bash
mvn spring-boot:run
```

Ou en construisant le `.jar` :

```bash
mvn clean package
java -jar target/gestion-bibliotheque-1.0.0.jar
```

### 4. Ouvrir dans le navigateur

```
http://localhost:8081
```

---

## Données de démonstration (optionnel)

Un fichier `src/main/resources/data.sql` contient des livres et utilisateurs d'exemple.

Pour les charger **une seule fois**, modifiez temporairement `application.properties` :

```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.sql.init.mode=always
```

Lancez l'application **une fois**, puis **remettez** les valeurs par défaut pour conserver vos données :

```properties
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=never
```

---

## Structure du projet

```
GestionBibliotheque/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/marieme/bibliotheque/
    │   ├── GestionBibliothequeApplication.java   (point d'entrée)
    │   ├── model/         (Livre, Utilisateur, Emprunt, StatutEmprunt)
    │   ├── repository/    (interfaces Spring Data JPA)
    │   ├── service/       (logique métier : emprunter / retourner)
    │   └── controller/    (contrôleurs Thymeleaf)
    └── resources/
        ├── application.properties
        ├── data.sql       (données de démo)
        ├── static/css/style.css
        └── templates/     (pages Thymeleaf)
            ├── fragments/layout.html
            ├── index.html
            ├── livres/        (liste, formulaire)
            ├── utilisateurs/  (liste, formulaire)
            └── emprunts/      (liste, nouveau)
```

---

## 🧭 Routes principales

| URL                          | Description                       |
|------------------------------|-----------------------------------|
| `/`                          | Tableau de bord                   |
| `/livres`                    | Liste des livres                  |
| `/livres/nouveau`            | Ajouter un livre                  |
| `/livres/{id}/modifier`      | Modifier un livre                 |
| `/utilisateurs`              | Liste des utilisateurs            |
| `/utilisateurs/nouveau`      | Ajouter un utilisateur            |
| `/emprunts`                  | Historique des emprunts           |
| `/emprunts/nouveau`          | Enregistrer un nouvel emprunt     |

---

##  Licence

Projet personnel à but pédagogique - libre de réutilisation.
```
