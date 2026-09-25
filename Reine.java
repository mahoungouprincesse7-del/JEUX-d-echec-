import java.util.ArrayList;
import java.util.List;

public class Reine extends Piece {

    public Reine(boolean noir) {
        super(noir);
    }

    public boolean isValidMove(Position start, Position end,
                                boolean capture, Plateau plateau)
                                throws InvalidMoveException {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        boolean commeTour = (dx == 0 || dy == 0);
        boolean commeFou  = (Math.abs(dx) == Math.abs(dy));
        if (!commeTour && !commeFou) {
            return false;
        }
        return cheminLibre(start, end, plateau);
    }

    public List<Position> getValidMoves(Position start, Plateau board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int[] dir : directions) {
            int x = start.getX() + dir[0];
            int y = start.getY() + dir[1];
            while (true) {
                Position pos = new Position(x, y);
                try {
                    Piece p = board.getPiece(pos);
                    if (p == null) {
                        moves.add(pos);
                    } else {
                        if (p.estNoir() != this.estNoir()) moves.add(pos);
                        break;
                    }
                } catch (InvalidMoveException e) {
                    break;
                }
                x += dir[0];
                y += dir[1];
            }
        }
        return moves;
    }
}