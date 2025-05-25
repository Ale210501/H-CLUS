package clustering;

import data.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
/**
 * La classe Cluster rappresenta un insieme di dati raggruppati (cluster).
 * Implementa le interfacce Iterable<Integer>, Cloneable e Serializable.
 */
public class Cluster implements Iterable<Integer>, Cloneable, Serializable {
    private Set<Integer> clusteredData = new TreeSet<>();

    /**
     * Aggiunge l'indice di un campione al cluster.
     *
     * @param id l'indice del campione da aggiungere al cluster.
     */
    void addData(int id) {
        clusteredData.add(id); // TreeSet gestisce in automatico i duplicati.
    }
    /**
     * Restituisce la dimensione del cluster.
     *
     * @return il numero di campioni nel cluster.
     */
    public int getSize() {
        return clusteredData.size();
    }
    /**
     * Restituisce un iteratore per gli elementi del cluster.
     *
     * @return un iteratore per gli indici dei campioni nel cluster.
     */
    @Override
    public Iterator<Integer> iterator() {
        return clusteredData.iterator();
    }

    /**
     * Clona il cluster corrente.
     *
     * @return una copia del cluster corrente.
     */
    @Override
    public Object clone() {
        Cluster copyC = new Cluster();
        for (Integer id : clusteredData) {
            copyC.addData(id);
        }
        return copyC;
    }

    /**
     * Unisce questo cluster con un altro cluster.
     *
     * @param c il cluster da unire con questo cluster.
     * @return un nuovo cluster risultante dall'unione di questo cluster e del
     *         cluster dato.
     */
    public Cluster mergeCluster(Cluster c) {
        Cluster newC = new Cluster();
        for (Integer id : this) {
            newC.addData(id);
        }
        for (Integer id : c) {
            newC.addData(id);
        }
        return newC;
    }
    /**
     * Restituisce una rappresentazione in stringa del cluster.
     *
     * @return una stringa che rappresenta il cluster.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Iterator<Integer> it = clusteredData.iterator();
        while (it.hasNext()) {
            str.append(it.next());
            if (it.hasNext()) str.append(",");
        }
        return str.toString();
    }

    /**
     * Restituisce una rappresentazione in stringa del cluster utilizzando un
     * oggetto Data.
     *
     * @param data l'oggetto Data utilizzato per ottenere le rappresentazioni dei
     *             campioni.
     * @return una stringa che rappresenta il cluster con i dettagli dei campioni.
     */
    public String toString(Data data) {
        StringBuilder str = new StringBuilder();
        for (Integer id : clusteredData) {
            str.append("<").append(data.getExample(id)).append(">");
        }
        return str.toString();
    }
}