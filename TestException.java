public class TestException {
    public static void main(String[] args) {
        try {
            throw new InvalidMoveException("Mouvement invalide !");
        } catch (InvalidMoveException e) {
            System.out.println("OK : " + e.getMessage());
        }
    }
}
