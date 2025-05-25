package database;

import data.Example;
import data.EmptySetException;
import data.MissingNumberException;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
/**
 * La classe TableData fornisce metodi per interrogare una tabella in un database
 * e ottenere una lista di oggetti {@link Example} memorizzati nella tabella.
 */
public class TableData {
    private DbAccess db;

    /**
     * Inizializza un nuovo oggetto TableData con un'istanza di {@link DbAccess}.
     *
     * @param db l'oggetto {@link DbAccess} utilizzato per connettersi al database.
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Interroga la tabella specificata nel database e restituisce una lista di
     * oggetti {@link Example} memorizzati nella tabella.
     *
     * @param table il nome della tabella da interrogare.
     * @return una lista di oggetti {@link Example} contenuti nella tabella.
     * @throws SQLException se si verifica un errore durante l'interrogazione del database.
     * @throws EmptySetException se la tabella è vuota.
     * @throws MissingNumberException se sono presenti attributi non numerici nella tabella.
     */
    public List<Example> getDistinctTransazioni(String table)
            throws SQLException, EmptySetException, MissingNumberException {
        List<Example> examples = new ArrayList<>();

        String query = "SELECT DISTINCT * FROM " + table;

        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Controlla se il result set è vuoto.
            if (!resultSet.next()) {
                throw new EmptySetException("La tabella è vuota.");
            }

            // Processa ogni riga del result set.
            do {
                Example example = new Example();
                //Presuppone che la tabella abbia un numero di colonne; regolare se necessario.
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
                examples.add(example);
            } while (resultSet.next());

        } catch (SQLException e) {
            throw new SQLException("Errore durante l'interrogazione del database: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }

        return examples;
    }
}