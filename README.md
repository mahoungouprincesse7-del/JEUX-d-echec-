# ♟️ Jeu d'échecs en Java (console)

Jeu d'échecs pour deux joueurs, jouable dans le terminal, développé en Java dans le cadre d'un projet universitaire (Licence, mars–avril 2026).
Objectif : mettre en pratique la **programmation orientée objet** (héritage, classes abstraites, polymorphisme, exceptions) et modéliser les règles de déplacement des pièces.

## Fonctionnalités

- Plateau 8×8 affiché en console, avec placement initial des 32 pièces
- Déplacements valides pour les 6 pièces : Pion, Tour, Cavalier, Fou, Reine, Roi
- Vérification que le chemin est libre (Tour, Fou, Reine)
- Pion : avance d'une case, double pas au premier coup, capture en diagonale
- Alternance des tours Blanc / Noir
- Détection de l'**échec** au roi
- **Annulation** du dernier coup (`undo`) grâce à une pile d'états
- **Sauvegarde / chargement** d'une partie dans un fichier texte
- Gestion des erreurs avec une exception personnalisée (`InvalidMoveException`)

## Architecture

| Classe | Rôle |
|---|---|
| `Piece` (abstraite) | Couleur, méthode abstraite `isValidMove()`, vérification de chemin libre |
| `Pion`, `Tour`, `Cavalier`, `Fou`, `Reine`, `Roi` | Règles de déplacement propres à chaque pièce |
| `Plateau` (abstraite) | Grille générique : ajout, déplacement, accès aux cases |
| `Echiquier` | Plateau 8×8 avec les conditions initiales du jeu d'échecs |
| `Position` | Coordonnées d'une case (ex. `"E2"` ↔ `(4, 1)`) |
| `InvalidMoveException` | Exception levée pour un coup ou une case invalide |
| `JeuxEchec` | Boucle de jeu : saisie, tours, échec, undo, save/load |
| `Test*.java` | Tests des positions, pièces, exceptions et mouvements possibles |

## Lancer le jeu

```bash
javac *.java
java JeuxEchec
```

Pour exécuter les tests (assertions activées) :

```bash
java -ea TestGetValidMoves
java TestPosition
```

## Commandes

| Commande | Effet |
|---|---|
| `E2 E4` | Déplace la pièce de E2 vers E4 |
| `undo` | Annule le dernier coup |
| `save partie.txt` | Sauvegarde la partie |
| `load partie.txt` | Charge une partie sauvegardée |
| `quit` | Quitte le jeu |

Affichage : pièces noires en majuscule (`Pi`, `To`…), pièces blanches en minuscule (`pi`, `to`…).

## Format de sauvegarde

Une ligne par pièce : `colonne,ligne,couleur,type`, puis le joueur dont c'est le tour.

```
4,1,BLANC,Pion
4,7,NOIR,Roi
JOUEUR,NOIR
```

Le fichier `partie.txt` du dépôt est un exemple de partie en cours.

## Pistes d'amélioration

- Roque, prise en passant et promotion du pion
- Détection de l'échec et mat et du pat
- Interdire les coups qui laissent son propre roi en échec
- Interface graphique (JavaFX / Swing)

## Compétences mobilisées

Java · POO (héritage, abstraction, polymorphisme) · Gestion d'exceptions · Structures de données (tableaux 2D, pile) · Lecture/écriture de fichiers · Tests

---
Auteure : **Princesse Mahoungou** – M1 Mathématiques Appliquées et Statistiques, Université de Lille
