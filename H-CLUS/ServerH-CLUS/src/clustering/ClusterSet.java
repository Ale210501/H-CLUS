package clustering;

import data.Data;
import distance.ClusterDistance;
import java.io.Serializable;

/**
 * La classe ClusterSet rappresenta un insieme di cluster e fornisce metodi per
 * gestirli. Implementa l'interfaccia Serializable.
 */
class ClusterSet implements Serializable {

    private Cluster C[];
    private int lastClusterIndex=0;
    /**
     * Costruttore che inizializza il ClusterSet con un numero specifico di cluster.
     *
     * @param k il numero massimo di cluster che possono essere contenuti nel
     *          ClusterSet.
     */
    ClusterSet(int k) {

        C=new Cluster[k];
    }
    /**
     * Aggiunge un cluster al ClusterSet, evitando duplicati.
     *
     * @param c il cluster da aggiungere.
     */
    void add(Cluster c){
        for(int j=0;j<lastClusterIndex;j++)
            if(c==C[j]) // evita duplicati.
                return;
        C[lastClusterIndex]=c;
        lastClusterIndex++;
    }
    /**
     * Restituisce il cluster all'indice specificato.
     *
     * @param i l'indice del cluster da restituire.
     * @return il cluster all'indice specificato.
     */
    Cluster get(int i) {
        return C[i];
    }


    /**
     * Restituisce una rappresentazione in stringa del ClusterSet.
     *
     * @return una stringa che rappresenta il ClusterSet.
     */
    public String toString(){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+="cluster"+i+":"+C[i]+"\n";

            }
        }
        return str;

    }

    /**
     * Restituisce una rappresentazione in stringa del ClusterSet utilizzando un
     * oggetto Data.
     *
     * @param data l'oggetto Data utilizzato per ottenere le rappresentazioni dei
     *             campioni.
     * @return una stringa che rappresenta il ClusterSet con i dettagli dei
     *         campioni.
     */
    String toString(Data data){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+="cluster"+i+":"+C[i].toString(data)+"\n";

            }
        }
        return str;

    }

    /*
    /determina la coppia di cluster più simili e li fonde in unico cluster
    //crea una nuova istanza di ClusterSet che contiene tutti i cluster dell’oggetto this
    /a meno dei due cluster fusi al posto dei
    /quali inserisce il cluster risultante dalla fusione
    */
    /**
     * Unisce i due cluster più vicini in base alla misura di distanza fornita, e
     * restituisce un nuovo insieme di cluster con un cluster in meno.
     *
     * @param distance l'istanza di {@code ClusterDistance} utilizzata per calcolare
     *                 la distanza tra cluster.
     * @param data     i dati contenenti gli esempi che formano i cluster.
     * @return un nuovo {@code ClusterSet} con i due cluster più vicini uniti.
     * @throws IllegalStateException se ci sono meno di due cluster nell'insieme.
     */

    ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) {

        if (lastClusterIndex < 2) {
            // eccezzione da poter inserire nel lab 3.
            throw new IllegalStateException("Non ci sono abbastanza cluster per effettuare una fusione");

        }

        int mergeIndex1 = -1;
        int mergeIndex2 = -1;
        double minDistance = Double.MAX_VALUE;

        // Trova la coppia di cluster più vicini
        for (int i = 0; i < lastClusterIndex; i++) {
            for (int j = i + 1; j < lastClusterIndex; j++) {
                double dist = distance.distance(C[i], C[j], data);
                if (dist < minDistance) {
                    minDistance = dist;
                    mergeIndex1 = i;
                    mergeIndex2 = j;
                }
            }
        }

        // Fonde i due cluster più vicini
        Cluster mergedCluster = C[mergeIndex1].mergeCluster(C[mergeIndex2]);

        // Crea un nuovo ClusterSet con un cluster in meno
        ClusterSet newClusterSet = new ClusterSet(lastClusterIndex - 1);

        // Aggiunge tutti gli altri cluster tranne i due cluster fusi
        for (int i = 0; i < lastClusterIndex; i++) {
            if (i != mergeIndex1 && i != mergeIndex2) // controlla se è diverso dal cluster1 e dal cluster2
            {
                newClusterSet.add(C[i]); // aggiunge i clusters in posizione di i

            }

            else if (i == mergeIndex1) // controlla se la prima posizione è il cluster 1 allora fa il merge

            {
                newClusterSet.add(mergedCluster);
            }
        }

        return newClusterSet;

    }

}