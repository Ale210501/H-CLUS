package data;

/**
 * La classe MissingNumberException è un'eccezione che viene lanciata
 * quando un attributo non numerico viene trovato all'interno della tabella.
 */
public class MissingNumberException extends Exception {
    /**
     * Costruttore della classe MissingNumberException.
     * Inizializza l'eccezione con un messaggio di dettaglio.
     *
     * @param message il messaggio di dettaglio che descrive l'errore.
     */
    public MissingNumberException(String message) {
        super(message);
    }
}