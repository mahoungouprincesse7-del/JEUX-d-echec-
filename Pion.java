public class Pion extends Piece {
    private boolean premierCoup = true; // true = le pion n'a pas encore bougé

    public Pion(boolean noir) {
        super(noir);
    }

    public boolean isValidMove(Position start, Position end,
                                boolean capture, Plateau plateau) throws InvalidMoveException {
        int dx = end.getX() - start.getX(); // différence de colonne
        int dy = end.getY() - start.getY(); // différence de ligne

        int direction = estNoir() ? -1 : 1; // blanc monte (+1), noir descend (-1)

        // avance d'une case tout droit, pas de capture
        if (!capture && dx == 0 && dy == direction) {
            premierCoup = false; // il a bougé
            return true;
        }

        // avance de deux cases au premier coup, tout droit, pas de capture
        if (!capture && dx == 0 && dy == 2 * direction && premierCoup) {
            // on vérifie que la case du milieu est vide
            Position milieu = new Position(start.getX(), start.getY() + direction);
            if (plateau.getPiece(milieu) != null) {
                return false; // case du milieu bloquée
            }
            premierCoup = false; // il a bougé
            return true;
        }

        // capture en diagonale d'une case
        if (capture && Math.abs(dx) == 1 && dy == direction) {
            premierCoup = false; // il a bougé
            return true;
        }

        return false; // tous les autres mouvements sont interdits
    }
}