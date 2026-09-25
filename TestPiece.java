public class TestPiece {
    public static void main(String[] args) {
        Pion pionBlanc = new Pion(false);
        Pion pionNoir = new Pion(true);
        Tour tourBlanc = new Tour(false);

        System.out.println("Pion blanc : " + pionBlanc); // pion
        System.out.println("Pion noir  : " + pionNoir);  // PiOn
        System.out.println("Tour blanc : " + tourBlanc); // tour

        // test couleur
        System.out.println("Pion noir ? " + pionNoir.estNoir()); // true
    }
}
