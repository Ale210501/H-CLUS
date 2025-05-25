package clustering;

import data.Data;
import distance.ClusterDistance;

import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * La classe HierachicalClusterMiner esegue il clustering gerarchico e gestisce un dendrogramma.
 * Implementa l'interfaccia Serializable.
 */
public class HierachicalClusterMiner implements Serializable {

    private Dendrogram dendrogram;
    /**
     * Costruttore che inizializza un HierachicalClusterMiner con una profondità specificata.
     *
     * @param depth la profondità del dendrogramma.
     * @throws InvalidDepthException se la profondità è minore o uguale a zero.
     */
    public HierachicalClusterMiner(int depth) throws InvalidDepthException {
        if (depth <= 0) {
            throw new InvalidDepthException("La profondità deve essere maggiore di zero.");
        }
        dendrogram = new Dendrogram(depth);
    }
    /**
     * Restituisce una rappresentazione in stringa del dendrogramma.
     *
     * @return una stringa che rappresenta il dendrogramma.
     */
    public String toString() {

        return dendrogram.toString();
    }
    /**
     * Restituisce una rappresentazione in stringa del dendrogramma utilizzando un oggetto Data.
     *
     * @param data l'oggetto Data utilizzato per ottenere le rappresentazioni dei campioni.
     * @return una stringa che rappresenta il dendrogramma con i dettagli dei campioni.
     */
    public String toString(Data data) {

        return dendrogram.toString(data);
    }


    /**
     * Esegue il clustering gerarchico sui dati forniti.
     * Crea il livello base del dendrogramma con ogni esempio in un cluster separato,
     * e per ogni livello successivo fonde i due cluster più vicini.
     *
     * @param data l'oggetto Data contenente i dati da clusterizzare.
     * @param distance l'oggetto ClusterDistance utilizzato per calcolare la distanza tra i cluster.
     * @throws InvalidSizeException se il numero di esempi nei dati è inferiore a due.
     */
    public void mine(Data data, ClusterDistance distance) throws InvalidSizeException{
        int numExamples = data.getNumberOfExamples();
        ClusterSet clusterSet = new ClusterSet(numExamples);

        // Inizializza il livello base del dendrogramma
        for (int i = 0; i < numExamples; i++) {
            Cluster cluster = new Cluster();
            cluster.addData(i);
            clusterSet.add(cluster);
        }

        dendrogram.setClusterSet(clusterSet, 0);

        // Esegue il clustering per ciascun livello successivo
        for (int level = 1; level < dendrogram.getDepth(); level++) {
            ClusterSet previousLevelClusterSet = dendrogram.getClusterSet(level - 1);
            if (previousLevelClusterSet != null) {
                ClusterSet newClusterSet = previousLevelClusterSet.mergeClosestClusters(distance, data);
                dendrogram.setClusterSet(newClusterSet, level);
            }
        }
    }

    /**
     * Carica un'istanza serializzata di HierachicalClusterMiner da un file.
     *
     * @param fileName il nome del file da cui caricare l'istanza.
     * @return l'istanza di HierachicalClusterMiner caricata.
     * @throws FileNotFoundException se il file specificato non esiste.
     * @throws IOException se si verifica un errore di I/O durante il caricamento.
     * @throws ClassNotFoundException se la classe del oggetto serializzato non può essere trovata.
     */
    public static HierachicalClusterMiner loadHierachicalClusterMiner(String fileName) throws FileNotFoundException, IOException,ClassNotFoundException{
        HierachicalClusterMiner miner = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            miner = (HierachicalClusterMiner) ois.readObject();
        }

        return miner;
    }

    /**
     * Salva un'istanza serializzata di HierachicalClusterMiner su un file.
     *
     * @param fileName il nome del file su cui salvare l'istanza.
     * @throws FileNotFoundException se il file specificato non può essere creato o aperto.
     * @throws IOException se si verifica un errore di I/O durante il salvataggio.
     */
    public void salva(String fileName)throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (Exception e) {
        System.out.println("Error saving file: " + e.getMessage());
    }
    }

}