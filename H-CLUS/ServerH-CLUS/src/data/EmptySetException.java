package data;

/**
 * La classe EmptySetException rappresenta un'eccezione che viene lanciata
 * quando un insieme di dati(tabella) è vuoto.
 */
public class EmptySetException extends Exception {

    /**
     * Costruttore della classe EmptySetException.
     *
     * @param message il messaggio di dettaglio che descrive l'errore.
     */
    public EmptySetException(String message) {
        super(message);
    }
}