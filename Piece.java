public abstract class Piece {
    private final boolean noir;

    public Piece(boolean noir) {
        this.noir = noir;
    }

    public boolean estNoir() {
        return noir;
    }

    public abstract boolean isValidMove(Position start,
                                        Position end,
                                        boolean capture,
                                        Plateau plateau) throws InvalidMoveException;

    protected boolean cheminLibre(Position start, Position end, Plateau plateau) throws InvalidMoveException {
        int dx = Integer.signum(end.getX() - start.getX());
        int dy = Integer.signum(end.getY() - start.getY());

        int x = start.getX() + dx;
        int y = start.getY() + dy;

        while (x != end.getX() || y != end.getY()) {
            if (plateau.getPiece(new Position(x, y)) != null) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }

    @Override
    public String toString() {
        String name = this.getClass().getSimpleName();
        String shortName;
        if (name.length() >= 2) {
            shortName = name.substring(0, 2);
        } else {
            shortName = name;
        }
        if (noir) {
            return shortName.substring(0, 1).toUpperCase() + shortName.substring(1);
        } else {
            return shortName.toLowerCase();
        }
    }
}