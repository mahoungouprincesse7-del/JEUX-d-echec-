public class Echiquier extends Plateau {

    // crée un échiquier de 8x8
    public Echiquier() {
        super(8, 8);
    }

    // place toutes les pièces à leur position de départ
    protected void conditionsInitiales() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                cases[x][y] = null; // on vide toutes les cases d'abord
            }
        }
        // ligne 1 : pièces blanches
        cases[0][0] = new Tour(false);     // A1
        cases[1][0] = new Cavalier(false); // B1
        cases[2][0] = new Fou(false);      // C1
        cases[3][0] = new Reine(false);    // D1
        cases[4][0] = new Roi(false);      // E1
        cases[5][0] = new Fou(false);      // F1
        cases[6][0] = new Cavalier(false); // G1
        cases[7][0] = new Tour(false);     // H1

        // ligne 2 : pions blancs
        for (int x = 0; x < 8; x++) {
            cases[x][1] = new Pion(false);
        }

        // ligne 7 : pions noirs
        for (int x = 0; x < 8; x++) {
            cases[x][6] = new Pion(true);
        }

        // ligne 8 : pièces noires
        cases[0][7] = new Tour(true);      // A8
        cases[1][7] = new Cavalier(true);  // B8
        cases[2][7] = new Fou(true);       // C8
        cases[3][7] = new Reine(true);     // D8
        cases[4][7] = new Roi(true);       // E8
        cases[5][7] = new Fou(true);       // F8
        cases[6][7] = new Cavalier(true);  // G8
        cases[7][7] = new Tour(true);      // H8
    }
}