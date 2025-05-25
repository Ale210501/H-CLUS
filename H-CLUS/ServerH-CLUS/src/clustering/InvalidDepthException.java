package clustering;

public class InvalidDepthException extends Exception {
    /**
     * Eccezione che viene lanciato quando si inserisce una profondità maggiore
     * rispetto al numero di esempi memorizzati nel dataset.
     */
    private static final long serialVersionUID = 1L;
    //versione seriale.

    public InvalidDepthException(String message) {
        super(message);
    }
}