public class Tour extends Piece {

    public Tour(boolean noir) {
        super(noir); // on dit à Piece si la tour est noire ou blanche
    }

    public boolean isValidMove(Position start, Position end,
                                boolean capture, Plateau plateau) {
        int dx = end.getX() - start.getX(); // différence de colonne
        int dy = end.getY() - start.getY(); // différence de ligne

        if (dx != 0 && dy != 0) {
            return false; // la tour doit rester sur la même ligne OU la même colonne
        }
        return true; // mouvement valide (ligne droite)
    }
}