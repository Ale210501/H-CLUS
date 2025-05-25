package clustering;

public class InvalidSizeException extends Exception {
    /**
     * Eccezione che viene lanciata se si prova a calcolare la distanza tra due
     * esempi di diversa dimensione.
     */
    private static final long serialVersionUID = 1L;
    //versione seriale.

    public InvalidSizeException(String message) {
        super(message);
    }
}