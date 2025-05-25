package data;

import clustering.InvalidSizeException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * La classe Example rappresenta un esempio che contiene una lista di valori reali.
 * Implementa l'interfaccia Iterable per consentire l'iterazione sui valori,
 * e Serializable per permetterne la serializzazione.
 */
public class Example implements Iterable<Double>, Serializable {
    private List<Double> example; //lista di valori reali

    /**
     * Costruttore della classe Example.
     * Inizializza example come una lista vuota.
     */
    public Example() {

        example = new LinkedList<>();
    }
    /**
     * Restituisce un iteratore per i valori dell'esempio.
     *
     * @return un iteratore per i valori dell'esempio.
     */
    public Iterator<Double> iterator() {

        return example.iterator();
    }
    /**
     * Aggiunge un valore all'esempio.
     *
     * @param v il valore da aggiungere.
     */
    public void add(Double v) {

        example.add(v);
    }

    /**
     * Restituisce il valore presente in una data posizione nell'esempio.
     *
     * @param index la posizione del valore da restituire.
     * @return il valore presente nella posizione specificata.
     */
    Double get(int index) {

        return example.get(index);
    }

    /**
     * Calcola la distanza euclidea tra l'esempio corrente e un nuovo esempio. Se i
     * due esempi non hanno la stessa dimensione, viene sollevata un'eccezione
     * {@code InvalidSizeException}.
     *
     * @param newE il nuovo esempio con cui calcolare la distanza.
     * @return la distanza euclidea tra i due esempi.
     */

    public double distance(Example newE) {

        double tot = 0.0;

        Iterator<Double> it1 = this.iterator();
        Iterator<Double> it2 = newE.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            double differenza = it1.next() - it2.next();

            tot += differenza * differenza;
        }
        return tot;

    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'esempio.
     *
     * @return una stringa che rappresenta il contenuto dell'esempio.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i=0; i<this.example.size(); i++) {
            sb.append(this.example.get(i));
            if (i < this.example.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}