public class InvalidMoveException extends Exception {
    // constructeur qui prend un message d'erreur
    public InvalidMoveException(String message) {
        super(message); // on passe le message à la classe Exception parente
    }
}