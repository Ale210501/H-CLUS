package com.progetto.extension.extension;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe contenente il metodo principale main, apre la finestra iniziale di connessione al Server.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception  {
        com.progetto.extension.extension.Utility.newWindow(stage, getClass(), "/com/progetto/extension/extension/H-clus.fxml", "H-Clus");
    }

    public static void main(String[] args) {
        launch(args);
    }
}