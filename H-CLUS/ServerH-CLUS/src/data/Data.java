package data;

import clustering.InvalidSizeException;
import database.DatabaseConnectionException;
import database.DbAccess;

import java.sql.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * La classe Data rappresenta un insieme di esempi letti da una tabella di un database.
 */
public class Data {
    private List<Example> data = new ArrayList<>(); // Rappresenta il dataset.
    private int numberOfExamples; // Rappresenta il numero di esempi nel dataset.

    /**
     * Costruttore che legge gli esempi da una tabella del database.
     * @param tableName la tabella da cui leggere i dati.
     * @throws NoDataException se si verifica un errore durante la lettura dei dati.
     */
    public Data(String tableName) throws NoDataException {
        try {
            DbAccess dbAccess = new DbAccess(); // Presupponendo che esista una classe DbAccess.
            String query = "SELECT * FROM " + tableName;

            try (Connection connection = dbAccess.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                // verifica se il result set è vuoto.
                if (!resultSet.next()) {
                    throw new EmptySetException("La tabella è vuota.");
                }

                do {
                    Example example = new Example();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        if (!(resultSet.getMetaData().getColumnType(i) == Types.INTEGER ||
                                resultSet.getMetaData().getColumnType(i) == Types.FLOAT ||
                                resultSet.getMetaData().getColumnType(i) == Types.DOUBLE ||
                                resultSet.getMetaData().getColumnType(i) == Types.DECIMAL ||
                                resultSet.getMetaData().getColumnType(i) == Types.NUMERIC ||
                                resultSet.getMetaData().getColumnType(i) == Types.BIGINT ||
                                resultSet.getMetaData().getColumnType(i) == Types.SMALLINT ||
                                resultSet.getMetaData().getColumnType(i) == Types.TINYINT ||
                                resultSet.getMetaData().getColumnType(i) == Types.REAL)) {
                            throw new MissingNumberException("Attributo non numerico trovato.");
                        }
                        example.add(resultSet.getDouble(i));
                    }
                    data.add(example);
                } while (resultSet.next());

                // Aggiorna il numero degli esempi.
                numberOfExamples = data.size();
            } catch (SQLSyntaxErrorException e) {
                throw new NoDataException("La tabella '" + tableName + "' non esiste nel database.");
            } catch (DatabaseConnectionException e) {
                throw new RuntimeException(e);
            } catch (EmptySetException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (MissingNumberException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  Restituisce il numeri di esempi nel dataset.
     * @return il numero di esempi.
     */
    public int getNumberOfExamples() {
        return this.numberOfExamples;
    }

    /**
     * Restituisce l'esempio all'indice specificato.
     *
     * @param exampleIndex l'indice dell'esempio da restituire.
     * @return l'esempio all'indice specificato.
     */
    public Example getExample(int exampleIndex) {
        return this.data.get(exampleIndex);
    }

    /**
     * Metodo per il calolo della matrice triangolare superiore delle distanze
     * Euclidee.
     *
     * @return la matrice triangolare superiore delle distanze euclidee.

     */
    public double[][] distance() {
        int numExamples = data.size();
        double[][] distances = new double[numExamples][numExamples];

        // Calcola e salva le distanze tra ogni coppia di esempi.
        for (int i = 0; i < numExamples; i++) {

            for (int j = i + 1; j < numExamples; j++) {
                distances[i][j] = this.data.get(i).distance(this.data.get(j));
            }
        }

        return distances;
    }


    /**
     * Crea una stringa che rappresenta gli esempi memorizzati nell'attributo data, correttamente enumerati.
     *
     * @return una stringa che rappresenta gli esempi.
     */

    @Override

    public String toString() {

        StringBuilder sb = new StringBuilder();
        Iterator<Example> iterator = data.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            sb.append("Example ").append(index).append(": ").append(iterator.next().toString()).append("\n");
            index = index + 1;
        }
        return sb.toString();

    }
    /**
     * Metodo principale per il test della classe Data.
     *
     * @param args gli argomenti della riga di comando.
     */
    public static void main(String[] args) {
        try {
            Data trainingSet = new Data("exampleTab");
            System.out.println(trainingSet);
            double[][] distanceMatrix = trainingSet.distance();
            System.out.println("Distance matrix:\n");
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = 0; j < distanceMatrix.length; j++)
                    System.out.print(distanceMatrix[i][j] + "\t");
                System.out.println("");
            }
        } catch (NoDataException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}