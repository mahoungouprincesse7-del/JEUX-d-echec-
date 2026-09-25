import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class JeuxEchec {

    private Echiquier echiquier;                     // le plateau de jeu
    private boolean noirJoue = false;                // false = blanc joue, true = noir joue
    private Stack<MoveState> pileCoups = new Stack<>(); // pile pour stocker les coups (pour undo)

    public static void main(String[] args) {
        new JeuxEchec().run(); // on crée le jeu et on le lance
    }

    public JeuxEchec() {
        echiquier = new Echiquier(); // on crée un échiquier avec les pièces de départ
    }

    public void run() {
        Scanner sc = new Scanner(System.in); // pour lire ce que tape le joueur

        while (true) { // boucle infinie, on tourne jusqu'à "quit"
            System.out.println(echiquier); // on affiche le plateau

            if (estEnEchec()) { // on vérifie si le joueur courant est en échec
                System.out.println("*** ECHEC au " + (noirJoue ? "NOIR" : "BLANC") + " ! ***");
            }

            // on affiche à qui c'est le tour
            System.out.print((noirJoue ? "Noir" : "Blanc") + " joue : ");
            String input = sc.nextLine().trim(); // on lit ce que tape le joueur

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Partie terminee."); // le joueur veut quitter
                break; // on sort de la boucle
            }

            if (input.equalsIgnoreCase("undo")) {
                undo(); // le joueur veut annuler son dernier coup
                continue; // on repart au début de la boucle
            }

            if (input.toLowerCase().startsWith("save ")) {
                String fichier = input.substring(5).trim(); // on récupère le nom du fichier
                sauvegarde(fichier); // on sauvegarde la partie
                continue;
            }

            if (input.toLowerCase().startsWith("load ")) {
                String fichier = input.substring(5).trim(); // on récupère le nom du fichier
                chargement(fichier); // on charge la partie
                continue;
            }

            // coup normal ex: "D2 D4"
            String[] parts = input.split("\\s+"); // on sépare les deux cases
            if (parts.length != 2) {
                System.out.println("Format invalide. Exemple : D2 D4"); // mauvais format
                continue;
            }

            try {
                Position start = new Position(parts[0].toUpperCase()); // case de départ
                Position end   = new Position(parts[1].toUpperCase()); // case d'arrivée

                MoveState etat = new MoveState(echiquier, noirJoue); // on sauvegarde l'état avant le coup

                echiquier.joue(start, end, noirJoue); // on joue le coup

                pileCoups.push(etat); // coup valide, on empile l'état pour pouvoir annuler
                noirJoue = !noirJoue; // on change de joueur

            } catch (InvalidMoveException e) {
                System.out.println("Erreur : " + e.getMessage()); // coup invalide, on affiche l'erreur
            }
        }

        sc.close(); // on ferme le scanner
    }

    // annule le dernier coup
    private void undo() {
        if (pileCoups.isEmpty()) {
            System.out.println("Aucun coup a annuler."); // pas de coup à annuler
            return;
        }
        MoveState etat = pileCoups.pop();  // on récupère l'état avant le dernier coup
        echiquier = etat.restaurer();      // on remet le plateau comme avant
        noirJoue  = etat.noirJoue;        // on remet le bon joueur
        System.out.println("Coup annule.");
    }

    // sauvegarde la partie dans un fichier texte
    private void sauvegarde(String nomFichier) {
        try (PrintWriter writer = new PrintWriter(nomFichier)) {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    try {
                        Piece p = echiquier.getPiece(new Position(x, y));
                        if (p != null) {
                            // on écrit : colonne, ligne, couleur, type de pièce
                            writer.println(x + "," + y + "," +
                                (p.estNoir() ? "NOIR" : "BLANC") + "," +
                                p.getClass().getSimpleName());
                        }
                    } catch (InvalidMoveException ignored) {}
                }
            }
            writer.println("JOUEUR," + (noirJoue ? "NOIR" : "BLANC")); // on sauvegarde à qui c'est le tour
            System.out.println("Sauvegarde : " + nomFichier);
        } catch (Exception e) {
            System.out.println("Sauvegarde echouee : " + e.getMessage());
        }
    }

    // charge une partie depuis un fichier texte
    private void chargement(String nomFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            echiquier = new Echiquier(); // on recrée un échiquier vide
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    try {
                        echiquier.ajoute(null, new Position(x, y)); // on vide toutes les cases
                    } catch (InvalidMoveException ignored) {}
                }
            }
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith("JOUEUR,")) {
                    noirJoue = ligne.endsWith("NOIR"); // on récupère à qui c'est le tour
                    continue;
                }
                String[] data = ligne.split(","); // on découpe la ligne
                if (data.length == 4) {
                    int x = Integer.parseInt(data[0]);   // colonne
                    int y = Integer.parseInt(data[1]);   // ligne
                    boolean noir = data[2].equals("NOIR"); // couleur
                    Piece p = creerPiece(data[3], noir); // on crée la pièce
                    if (p != null) {
                        try {
                            echiquier.ajoute(p, new Position(x, y)); // on la place sur le plateau
                        } catch (InvalidMoveException ignored) {}
                    }
                }
            }
            pileCoups.clear(); // on vide la pile undo car c'est une nouvelle partie
            System.out.println("Charge : " + nomFichier);
        } catch (Exception e) {
            System.out.println("Chargement echoue : " + e.getMessage());
        }
    }

    // crée la bonne pièce selon son nom
    private Piece creerPiece(String nom, boolean noir) {
        switch (nom) {
            case "Pion":     return new Pion(noir);
            case "Tour":     return new Tour(noir);
            case "Cavalier": return new Cavalier(noir);
            case "Fou":      return new Fou(noir);
            case "Reine":    return new Reine(noir);
            case "Roi":      return new Roi(noir);
            default:         return null; // nom inconnu
        }
    }

    // vérifie si le roi du joueur courant est attaqué
    private boolean estEnEchec() {
        Position roiPos = trouverRoi(noirJoue); // on cherche où est le roi
        if (roiPos == null) return false; // pas de roi trouvé

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                try {
                    Piece p = echiquier.getPiece(new Position(x, y));
                    if (p != null && p.estNoir() != noirJoue) { // pièce adverse
                        Position from = new Position(x, y);
                        if (p.isValidMove(from, roiPos, true, echiquier)) {
                            return true; // cette pièce peut atteindre le roi = échec
                        }
                    }
                } catch (InvalidMoveException ignored) {}
            }
        }
        return false; // aucune pièce adverse n'attaque le roi
    }

    // cherche la position du roi d'une couleur donnée
    private Position trouverRoi(boolean noir) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                try {
                    Piece p = echiquier.getPiece(new Position(x, y));
                    if (p instanceof Roi && p.estNoir() == noir) {
                        return new Position(x, y); // on a trouvé le roi
                    }
                } catch (InvalidMoveException ignored) {}
            }
        }
        return null; // roi pas trouvé
    }

    // classe interne qui sauvegarde l'état du plateau à un moment donné
    private static class MoveState {
        private final Piece[][] plateau; // copie du plateau
        final boolean noirJoue;          // à qui c'était le tour

        MoveState(Echiquier e, boolean noirJoue) {
            this.noirJoue = noirJoue;
            this.plateau = new Piece[8][8];
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    try {
                        plateau[y][x] = e.getPiece(new Position(x, y)); // on copie chaque case
                    } catch (InvalidMoveException ignored) {}
                }
            }
        }

        // recrée un échiquier à partir de la copie sauvegardée
        Echiquier restaurer() {
            Echiquier nouveau = new Echiquier(); // nouvel échiquier vide
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    try {
                        nouveau.ajoute(plateau[y][x], new Position(x, y)); // on remet chaque pièce
                    } catch (InvalidMoveException ignored) {}
                }
            }
            return nouveau;
        }
    }
}