package com.progetto.extension.extension;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che gestisce il menu principale,
 * fornisce la scelta tra lettura e scoperta di un dendrogramma di cluster.
 */
public class ControllerMenu {
    /**
     * metodo che apre la finestra per l'opzione di Scoperta
     * @param actionEvent
     * @throws IOException
     */
    public void learningFromDb(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        com.progetto.extension.extension.Utility.newWindow(stage, getClass(), "/com/progetto/extension/extension/DBConnection.fxml", "H-Clus");
    }

    /**
     * metodo che apre la finestra per l'opzione di Lettura
     * @param actionEvent
     * @throws IOException
     */
    public void learningFromFile(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        com.progetto.extension.extension.Utility.newWindow(stage, getClass(), "/com/progetto/extension/extension/Lettura.fxml", "H-Clus");
    }
}
