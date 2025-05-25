package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che modella il server e la connessione con i client che ne fanno richiesta.
 * La fase di accettazione delle richieste multiple dei client sono gestite sul thread principale.
 */
public class MultiServer {
    /**
     * mantiene il valore di porta in cui il server è in ascolto
     */
    private int PORT;

    /**
     * costruttore di classe. inizializza la porta ed invoca run().
     */
    public MultiServer(int port) throws IOException {
        this.PORT = port;
        run();
    }

    /**
     * Fase di attivazione del server.
     * Comportamento: Istanzia un oggetto della classe ServerSocket che pone in attesa di richiesta di connessioni da parte del client.
     * Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
     * @throws IOException Eccezioni per l'I/O di oggetti.
     */
    private void run() throws IOException {
        // Istanziazione di un oggetto ServerSocket.
        ServerSocket sSocket = new ServerSocket(PORT);
        System.out.println("Server attivato.");
        try {
            // Ciclo infinito perche' il server e' sempre in attesa di nuovi client.
            while(true) {
                // Il server accetta sempre nuovi client con la quale instaura una connessione.
                Socket socket = sSocket.accept();
                try {
                    // Istanziazione di un oggetto ServerOneClient per gestire il rapporto con il singolo client.
                    new ServerOneClient(socket);
                    System.out.println("Client collegato con successo.");
                } catch(IOException e) {
                    // Se il rapporto con il singolo client fallisce, il rapporto con il client viene chiuso.
                    socket.close();
                    System.out.println("Client disconnesso.");
                }
            }
        } finally {
            sSocket.close();
        }
    }

    /**
     * Comportamento: istanzia un oggetto di tipo MultiServer.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new MultiServer(new Integer(args[0]).intValue());
    }
}