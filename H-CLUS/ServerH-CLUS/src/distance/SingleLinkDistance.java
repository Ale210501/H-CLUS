package distance;

import clustering.Cluster;
import data.Data;
import data.Example;

/**
 * Calcola la distanza singola tra due cluster.
 */
public class SingleLinkDistance implements ClusterDistance {

    /**
     * Calcola la distanza singola tra due cluster.
     *
     * @param c1 il primo cluster.
     * @param c2 il secondo cluster.
     * @param d  l'oggetto {@link Data} contenente gli esempi utilizzati per calcolare le distanze.
     * @return la distanza singola tra i due cluster.
     */
    public double distance(Cluster c1, Cluster c2, Data d) {
        double min = Double.MAX_VALUE;

        for (Integer id1 : c1) {
            Example e1 = d.getExample(id1);
            for (Integer id2 : c2) {
                double distance = e1.distance(d.getExample(id2));
                if (distance < min) {
                    min = distance;
                }
            }
        }
        return min;
    }
}