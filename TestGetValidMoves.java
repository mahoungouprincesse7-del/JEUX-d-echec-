
import java.util.List;

public class TestGetValidMoves {
    public static void main(String[] args) throws InvalidMoveException {

        // Test 1 : 27 mouvements depuis D4 sur plateau vide
        Echiquier e = new Echiquier();
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                e.cases[x][y] = null;

        Reine r = new Reine(false);
        Position centre = new Position("D4");
        e.ajoute(r, centre);

        List<Position> moves = r.getValidMoves(centre, e);
        assert moves.size() == 27 : "ECHEC test 1 : attendu 27, obtenu " + moves.size();
        System.out.println("Test 1 OK - " + moves.size() + " mouvements");

        // Test 2 : liste jamais null
        assert moves != null : "ECHEC test 2 : liste null";
        System.out.println("Test 2 OK - liste non null");

        // Test 3 : la case de départ n'est pas dans la liste
        assert !moves.contains(centre) : "ECHEC test 3 : start dans la liste";
        System.out.println("Test 3 OK - start absent");

        // Test 4 : un allié bloque le chemin
        e.ajoute(new Pion(false), new Position("D6"));
        moves = r.getValidMoves(centre, e);
        boolean d7present = moves.stream().anyMatch(p -> p.toString().equals("D7"));
        assert !d7present : "ECHEC test 4 : D7 accessible alors que D6 bloque";
        System.out.println("Test 4 OK - obstacle bloque bien");

        System.out.println("Tous les tests passent.");
    }
}