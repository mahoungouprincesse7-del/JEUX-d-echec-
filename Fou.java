public class Fou extends Piece {

    public Fou(boolean noir) {
        super(noir); // on dit à Piece si le fou est noir ou blanc
    }

    public boolean isValidMove(Position start, Position end,
                                boolean capture, Plateau plateau)
                                throws InvalidMoveException {
        int dx = end.getX() - start.getX(); // différence de colonne
        int dy = end.getY() - start.getY(); // différence de ligne

        if (Math.abs(dx) != Math.abs(dy)) {
            return false; // pas en diagonale, mouvement interdit
        }
        return cheminLibre(start, end, plateau); // vérifie qu'il n'y a pas d'obstacle
    }
}