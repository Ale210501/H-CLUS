package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * La classe TableSchema rappresenta lo schema di una tabella del database,
 * fornendo informazioni sulle colonne e i loro tipi di dati.
 */
public class TableSchema {
    private DbAccess db;

    /**
     * Rappresenta una colonna della tabella con il nome e il tipo di dati associati.
     */
    public class Column{
        private String name;
        private String type;

        /**
         * Costruisce un oggetto Column con il nome e il tipo specificati.
         *
         * @param name il nome della colonna.
         * @param type il tipo di dati della colonna (string o number).
         */
        Column(String name,String type){
            this.name=name;
            this.type=type;
        }
        /**
         * Restituisce il nome della colonna.
         *
         * @return il nome della colonna.
         */
        public String getColumnName() {

            return name;
        }

        /**
         * Verifica se il tipo della colonna è numerico.
         *
         * @return true se il tipo della colonna è "number", false altrimenti.
         */

        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Restituisce una rappresentazione testuale della colonna nel formato "nome:tipo".
         *
         * @return una stringa che rappresenta la colonna nel formato "nome:tipo".
         */
        public String toString() {
            return name+":"+type;
        }
    }
    List<Column> tableSchema=new ArrayList<>();

    /**
     * Costruisce uno schema di tabella utilizzando l'accesso al database specificato e il nome della tabella.
     *
     * @param db        l'oggetto {@link DbAccess} utilizzato per connettersi al database.
     * @param tableName il nome della tabella di cui si vuole ottenere lo schema.
     * @throws SQLException              se si verifica un errore durante l'interrogazione del database.
     * @throws DatabaseConnectionException se non è possibile stabilire la connessione al database.
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException {
        this.db=db;
        HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
        //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
        // Mappa i tipi di dati SQL ai tipi di dati Java.
        mapSQL_JAVATypes.put("CHAR","string");
        mapSQL_JAVATypes.put("VARCHAR","string");
        mapSQL_JAVATypes.put("LONGVARCHAR","string");
        mapSQL_JAVATypes.put("BIT","string");
        mapSQL_JAVATypes.put("SHORT","number");
        mapSQL_JAVATypes.put("INT","number");
        mapSQL_JAVATypes.put("LONG","number");
        mapSQL_JAVATypes.put("FLOAT","number");
        mapSQL_JAVATypes.put("DOUBLE","number");


        // Ottiene la connessione al database e i metadati della tabella.
        Connection con=db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        //itera sul risultato per ottenere informazioni sulle colonne.
        while (res.next()) {

            if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
        }
        res.close();
    }

    /**
     * Restituisce il numero di attributi (colonne) nella tabella.
     *
     * @return il numero di attributi nella tabella.
     */
    public int getNumberOfAttributes(){
        return tableSchema.size();
    }
    /**
     * Restituisce l'oggetto {@link Column} corrispondente all'indice specificato.
     *
     * @param index l'indice della colonna desiderata.
     * @return l'oggetto {@link Column} corrispondente all'indice specificato.
     */
    public Column getColumn(int index){
        return tableSchema.get(index);
    }


}