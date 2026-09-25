public class Position {
    private final int x; // colonne (0 = A), final = on peut plus changer après
    private final int y; // ligne (0 = 1), final = on peut plus changer après

    // constructeur avec deux nombres directement
    public Position(int x, int y) {
        this.x = x; // on stocke la colonne
        this.y = y; // on stocke la ligne
    }

    // constructeur qui part d'une position existante et la décale
    public Position(Position p, int dx, int dy) {
        this.x = p.x + dx; // nouvelle colonne = ancienne + décalage
        this.y = p.y + dy; // nouvelle ligne = ancienne + décalage
    }

    // constructeur qui lit une String comme "D4"
    public Position(String s) {
        char colChar = s.charAt(0);        // on prend la première lettre ex: 'D'
        String rowPart = s.substring(1);   // on prend le chiffre ex: "4"
        this.x = Character.toUpperCase(colChar) - 'A'; // D - A = 3 donc x=3
        int row = Integer.parseInt(rowPart); // on convertit "4" en nombre 4
        this.y = row - 1; // on enlève 1 car on commence à 0 pas à 1
    }

    // retourne la colonne
    public int getX() {
        return x;
    }

    // retourne la ligne
    public int getY() {
        return y;
    }

    // convertit la position en texte ex: (3,3) devient "D4"
    public String toString() {
        char col = (char) ('A' + x); // 0 devient 'A', 1 devient 'B', etc.
        int row = y + 1;             // on remet +1 car les lignes commencent à 1
        return "" + col + row;       // on colle la lettre et le chiffre
    }
}