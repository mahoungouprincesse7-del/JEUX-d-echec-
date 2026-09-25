public class Cavalier extends Piece {

    public Cavalier(boolean noir) {
        super(noir); // on dit à Piece si le cavalier est noir ou blanc
    }

    public boolean isValidMove(Position start, Position end,
                                boolean capture, Plateau plateau) {
        int dx = Math.abs(end.getX() - start.getX()); // distance en colonne (toujours positive)
        int dy = Math.abs(end.getY() - start.getY()); // distance en ligne (toujours positive)

        // le cavalier fait un L : 2 cases dans un sens et 1 dans l'autre
        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
    }
}