public abstract class Plateau {
    protected Piece[][] cases; // tableau 2D qui contient toutes les pièces
    private int largeur;       // nombre de colonnes
    private int hauteur;       // nombre de lignes

    // constructeur : crée un plateau vide de la bonne taille
    protected Plateau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.cases = new Piece[largeur][hauteur]; // crée le tableau vide
        conditionsInitiales(); // place les pièces de départ
    }

    // méthode abstraite : chaque jeu place ses pièces à sa façon
    protected abstract void conditionsInitiales();

    // place une pièce sur une case
    protected void ajoute(Piece p, Position pos) throws InvalidMoveException {
        int x = pos.getX();
        int y = pos.getY();
        if (!dansPlateau(x, y)) {
            throw new InvalidMoveException("Position hors du plateau : " + pos); // case inexistante
        }
        cases[x][y] = p; // on met la pièce sur la case
    }

    // déplace une pièce d'une case à une autre
    protected void deplace(Position start, Position end) throws InvalidMoveException {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();
        if (!dansPlateau(x1, y1) || !dansPlateau(x2, y2)) {
            throw new InvalidMoveException("Coordonnées hors du plateau"); // case inexistante
        }
        Piece p = cases[x1][y1]; // on récupère la pièce à déplacer
        if (p == null) {
            throw new InvalidMoveException("Case de départ vide"); // pas de pièce à déplacer
        }
        cases[x2][y2] = p;    // on met la pièce sur la case d'arrivée
        cases[x1][y1] = null; // on vide la case de départ
    }

    // vérifie si une case est bien dans le plateau
    private boolean dansPlateau(int x, int y) {
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    // retourne la pièce sur une case (null si vide)
    public Piece getPiece(Position pos) throws InvalidMoveException {
        int x = pos.getX();
        int y = pos.getY();
        if (!dansPlateau(x, y)) {
            throw new InvalidMoveException("Position hors du plateau : " + pos); // case inexistante
        }
        return cases[x][y]; // retourne la pièce ou null si vide
    }

    // gère un coup complet : vérifie tout puis déplace
    public void joue(Position start, Position end, boolean noirQuiJoue)
            throws InvalidMoveException {
        Piece depart = getPiece(start); // on récupère la pièce sur la case de départ
        if (depart == null) {
            throw new InvalidMoveException("Case de départ vide"); // pas de pièce ici
        }
        if (depart.estNoir() != noirQuiJoue) {
            throw new InvalidMoveException("Ce n'est pas la bonne couleur"); // mauvaise pièce
        }
        Piece arrivee = null;
        try {
            arrivee = getPiece(end); // on regarde ce qu'il y a sur la case d'arrivée
        } catch (InvalidMoveException e) {
            throw e; // case hors plateau, on relance l'erreur
        }
        boolean capture = (arrivee != null); // il y a une pièce à capturer ?
        if (arrivee != null && arrivee.estNoir() == depart.estNoir()) {
            throw new InvalidMoveException("Case d'arrivée occupée par une pièce alliée"); // on peut pas manger ses propres pièces
        }
        if (!depart.isValidMove(start, end, capture, this)) {
            throw new InvalidMoveException("Mouvement non valide pour cette pièce"); // mouvement interdit
        }
        deplace(start, end); // tout est bon, on déplace la pièce
    }

    // affiche le plateau en texte avec les lettres et les chiffres
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = hauteur - 1; y >= 0; y--) { // on commence par le haut (ligne 8)
            sb.append(y + 1).append(" "); // numéro de la ligne
            for (int x = 0; x < largeur; x++) { // on parcourt chaque colonne
                Piece p = cases[x][y];
                if (p == null) {
                    sb.append(" . "); // case vide
                } else {
                    sb.append(p.toString()); // affiche la pièce
                }
                sb.append(" ");
            }
            sb.append("\n"); // nouvelle ligne
        }
        sb.append("   ");
        for (int x = 0; x < largeur; x++) {
            char c = (char) ('A' + x); // lettres A B C D E F G H
            sb.append(" ").append(c).append("  ");
        }
        sb.append("\n");
        return sb.toString();
    }
}