package server;

import clustering.HierachicalClusterMiner;
import clustering.InvalidDepthException;
import clustering.InvalidSizeException;

import data.Data;
import data.NoDataException;

import distance.AverageLinkDistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;

import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Questa classe gestisce la comunicazione con un singolo client attraverso socket,
 * consentendo di eseguire operazioni di mining di cluster gerarchici su dati provenienti
 * da un database o da file.
 */
public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private HierachicalClusterMiner hierachical;
    private Data data;


    /**
     * Costruttore che inizializza il thread per gestire la comunicazione con il client.
     *
     * @param s il socket associato alla connessione con il client.
     * @throws IOException se si verifica un errore di I/O durante l'inizializzazione.
     */
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    /**
     * Metodo principale del thread che gestisce le operazioni richieste dal client.
     * Le operazioni possibili sono:
     * - 0: caricamento di una tabella dal database;
     * - 1: apprendimento dei cluster dalla tabella del database;
     * - 2: salvataggio dei cluster in un file;
     * - 3: apprendimento dei cluster da un file.
     */
    public void run() {
        try {
            while (true) {
                int comando = (int) in.readObject();
                switch (comando) {

                    case 0:
                        handleStoreTableFromDb();
                        break;

                    case 1:
                        handleLearningFromDbTable();
                        handleStoreClusterInFile();
                        break;

                    case 2:
                        handleLearningFromFile();
                        break;
                }
            }
        } catch (EOFException e) {
            System.out.println("Connessione terminata dal client: " + e.getMessage());
        } catch (SocketException e) {
            System.out.println("Errore di socket: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore di I/O: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Connessione con il client terminata.");
            } catch (IOException e) {
                System.out.println("Errore durante la chiusura del socket: " + e.getMessage());
            }
        }
    }
    /**
     * Gestisce l'operazione di caricamento di una tabella dal database.
     * Invia una risposta al client indicando lo stato dell'operazione.
     *
     * @throws IOException se si verifica un errore di I/O durante l'operazione.
     */
    private void handleStoreTableFromDb() throws IOException {
        try {
            String tableName = (String) in.readObject();
            data = new Data(tableName);
            out.writeObject("OK");
        } catch (NoDataException e) {
            out.writeObject("Errore: impossibile trovare i dati per la tabella specificata.");
            System.out.println("Eccezione di dati non trovati: " + e.getMessage());
        } catch (FileNotFoundException e) {
            out.writeObject("Errore: file non trovato. Controlla il percorso del file.");
            System.out.println("File non trovato: " + e.getMessage());
        } catch (IOException e) {
            out.writeObject("Errore di I/O: problema durante l'interazione con il sistema di file.");
            System.out.println("Problema di I/O: " + e.getMessage());
        } catch (ClassCastException | ClassNotFoundException e) {
            out.writeObject("Errore di tipo: problema con il tipo di dati ricevuto.");
            System.out.println("Problema con il cast di classe: " + e.getMessage());
        }
    }

    /**
     * Gestisce l'operazione di apprendimento dei cluster dalla tabella del database.
     * Invia una risposta al client indicando lo stato dell'operazione e, in caso di successo,
     * i risultati del clustering.
     *
     * @throws IOException se si verifica un errore di I/O durante l'operazione.
     */
    private void handleLearningFromDbTable() throws IOException {
        try {
            int k = (int) in.readObject();
            int distanceType = (int) in.readObject();
            hierachical = new HierachicalClusterMiner(k);
            ClusterDistance distance;
            if (distanceType == 1) {
                distance = new SingleLinkDistance();
            } else {
                distance = new AverageLinkDistance();
            }
            hierachical.mine(data, distance);
            out.writeObject("OK");
            out.writeObject(hierachical.toString(data));
        } catch (InvalidDepthException e) {
            out.writeObject("Errore: la profondità specificata non è valida. Fornire un valore corretto.");
            System.out.println("Eccezione di profondità non valida: " + e.getMessage());
        } catch (InvalidSizeException e) {
            out.writeObject("Errore: la dimensione specificata non è valida. Verifica le impostazioni.");
            System.out.println("Eccezione di dimensione non valida: " + e.getMessage());
        } catch (NotSerializableException | ClassNotFoundException e) {
            out.writeObject("Errore: l'oggetto non può essere serializzato. Verifica la compatibilità dei dati.");
            System.out.println("Eccezione di non serializzabilità: " + e.getMessage());
        } catch (Exception e) {
            out.writeObject("Errore imprevisto: si è verificato un problema imprevisto. Verifica i dettagli: " + e.getMessage());
            System.out.println("Errore generico durante l'elaborazione: " + e.getMessage());
        }
    }

    /**
     * Gestisce l'operazione di apprendimento dei cluster da un file.
     * Invia una risposta al client indicando lo stato dell'operazione e, in caso di successo,
     * i risultati del clustering.
     *
     * @throws IOException se si verifica un errore di I/O durante l'operazione.
     */
    private void handleLearningFromFile() throws IOException {
        try {
            String fileName = (String) in.readObject();
            hierachical = HierachicalClusterMiner.loadHierachicalClusterMiner(fileName);
            out.writeObject("OK");
            out.writeObject(hierachical.toString(data));
        } catch (FileNotFoundException e) {
            out.writeObject("Errore: file non trovato. Controlla il percorso del file.");
            System.out.println("File non trovato: " + e.getMessage());
        } catch (IOException e) {
            out.writeObject("Errore di I/O: problema durante l'interazione con il sistema di file.");
            System.out.println("Problema di I/O: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            out.writeObject("Errore: classe non trovata. Verifica che tutte le dipendenze siano disponibili.");
            System.out.println("Eccezione di classe non trovata: " + e.getMessage());
        } catch (ClassCastException e) {
            out.writeObject("Errore di tipo: problema con il tipo di dati ricevuto.");
            System.out.println("Problema con il cast di classe: " + e.getMessage());
        }
    }
    private void handleStoreClusterInFile() throws IOException {
        try {
            String nomeFile = (String) in.readObject();
            hierachical.salva(nomeFile);
            out.writeObject("OK");
        } catch (Exception e) {
            out.writeObject("Errore di salvataggio: " + e.getMessage());
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}