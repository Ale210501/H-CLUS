package clustering;

import data.Data;
import java.io.Serializable;

/**
 * La classe Dendrogram rappresenta un dendrogramma utilizzato per la modellazione gerarchica dei cluster.
 * Implementa l'interfaccia Serializable.
 */
public class Dendrogram implements Serializable {

    private static final long serialVersionUID = 1L;
    private  ClusterSet tree[]; // Modella il dendrogramma

    /**
     * Costruttore che crea un vettore di dimensione depth con cui inizializza tree.
     *
     * @param depth la profondità del dendrogramma, ossia il numero di livelli.
     */
    Dendrogram(int depth) {
        this.tree = new ClusterSet[depth];
    }

    /**
     * Memorizza un ClusterSet nella posizione level di tree.
     *
     * @param c il ClusterSet da memorizzare.
     * @param level il livello del dendrogramma in cui memorizzare il ClusterSet.
     */
    void setClusterSet(ClusterSet c, int level) {
        this.tree[level] = c;
    }

    /**
     * Restituisce il ClusterSet memorizzato nella posizione level di tree.
     *
     * @param level il livello del dendrogramma da cui restituire il ClusterSet.
     * @return il ClusterSet al livello specificato.
     */
    ClusterSet getClusterSet(int level) {
        return this.tree[level];
    }

    /**
     * Restituisce la profondità del dendrogramma.
     *
     * @return la profondità del dendrogramma.
     */
    int getDepth() {
        return this.tree.length;
    }

    /**
     * Restituisce una rappresentazione in stringa del dendrogramma.
     *
     * @return una stringa che rappresenta il dendrogramma.
     */
    public String toString() {
        String v = "";
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                v += ("level" + i + ":\n" + tree[i] + "\n");
            } else {
                v += ("level" + i + ": null\n");
            }
        }
        return v;
    }

    /**
     * Restituisce una rappresentazione in stringa del dendrogramma utilizzando un oggetto Data.
     *
     * @param data l'oggetto Data utilizzato per ottenere le rappresentazioni dei campioni.
     * @return una stringa che rappresenta il dendrogramma con i dettagli dei campioni.
     */
    String toString(Data data) {
        String v = "";
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                v += ("level" + i + ":\n" + tree[i].toString(data) + "\n");
            } else {
                v += ("level" + i + ": null\n");
            }
        }
        return v;
    }
}