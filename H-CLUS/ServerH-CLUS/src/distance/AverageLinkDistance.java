package distance;

import clustering.Cluster;
import data.Data;

/**
 * Implementazione dell'interfaccia {@link ClusterDistance} che calcola la distanza media
 * tra due cluster utilizzando la distanza euclidea tra i loro esempi.
 */
public class AverageLinkDistance implements ClusterDistance {

    /**
     * Calcola la distanza media tra due cluster utilizzando la distanza euclidea tra tutti i loro esempi.
     *
     * @param c1   il primo cluster.
     * @param c2   il secondo cluster.
     * @param data l'oggetto {@link Data} contenente gli esempi utilizzati per calcolare le distanze.
     * @return la distanza media tra i due cluster.
     */
    public double distance(Cluster c1, Cluster c2, Data data) {
        double sumDistance = 0;
        int count = 0;

        // Itera su tutti gli esempi dei due cluster
        for (Integer id1 : c1) {
            for (Integer id2 : c2) {
                sumDistance += data.getExample(id1).distance(data.getExample(id2));
                count++;
            }
        }

        // Calcola la distanza media, gestendo il caso in cui non ci siano esempi
        return count > 0 ? sumDistance / count : 0;
    }
}