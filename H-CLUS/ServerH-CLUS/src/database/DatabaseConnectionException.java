package database;

/**
 * La classe DatabaseConnectionException è un'eccezione personalizzata che viene lanciata
 * quando si verifica un errore durante la connessione al database.
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Costruttore della classe DatabaseConnectionException.
     * Inizializza l'eccezione con un messaggio di dettaglio.
     *
     * @param message il messaggio di dettaglio che descrive l'errore.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }
}