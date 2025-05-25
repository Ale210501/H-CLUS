package distance;

import clustering.Cluster;
import data.Data;

/**
 * Interfaccia che definisce il contratto per calcolare la distanza tra due cluster.
 */
public interface ClusterDistance {

    /**
     * Calcola la distanza tra due cluster.
     *
     * @param c1 il primo cluster.
     * @param c2 il secondo cluster.
     * @param d  l'oggetto {@link Data} contenente gli esempi utilizzati per calcolare le distanze.
     * @return la distanza tra i due cluster.
     */
    double distance(Cluster c1, Cluster c2, Data d);
}