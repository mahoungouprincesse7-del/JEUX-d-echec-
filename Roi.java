public class Roi extends Piece {

    public Roi(boolean noir) {
        super(noir); // on dit à Piece si le roi est noir ou blanc
    }

    public boolean isValidMove(Position start, Position end,
                                boolean capture, Plateau plateau) {
        int dx = Math.abs(end.getX() - start.getX()); // distance en colonne (toujours positive)
        int dy = Math.abs(end.getY() - start.getY()); // distance en ligne (toujours positive)

        // le roi bouge d'une seule case dans n'importe quelle direction
        return dx <= 1 && dy <= 1 && (dx != 0 || dy != 0); // max 1 case, et doit bouger
    }
}