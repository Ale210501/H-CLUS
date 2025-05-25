package data;

/**
 * La classe NoDataException è un'eccezione personalizzata che viene lanciata
 * quando non ci sono dati disponibili o si verifica un errore durante la lettura
 * dei dati.
 */
public class NoDataException extends Throwable {

    /**
     * Costruttore della classe NoDataException.
     * Inizializza l'eccezione con un messaggio di dettaglio.
     *
     * @param message il messaggio di dettaglio che descrive l'errore.
     */
    public NoDataException(String message) {
        super(message);
    }
}