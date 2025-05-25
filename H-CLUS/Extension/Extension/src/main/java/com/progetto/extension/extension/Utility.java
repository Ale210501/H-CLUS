package com.progetto.extension.extension;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che implementa alcune funzionalità utili per l'applicazione,
 * in particolare l'apertura di tutte le nuove finestre e la comparsa di finestre di errore.
 */
public class Utility {
    /**
     * metodo che si occupa dell'apertura di una nuova finestra
     * @param stage Stage
     * @param type Class type
     * @param path path al file XML
     * @param name nome file XML per la finestra
     * @throws IOException
     */
    static void newWindow(Stage stage, Class<?> type, String path, String name) throws IOException, RuntimeException {
        Parent root = FXMLLoader.load(type.getResource(path));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(type.getResourceAsStream("/com/progetto/extension/extension/icon.png")));
        stage.setTitle(name);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent ->{
            SocketHandler.closeSocket();
        });
    }
    /**
     * metodo che si occupa dell'apertura delle finestre di errore
     * @param Title Titolo
     * @param Header Header
     * @param Content Contenuto da visualizzare nella finestra di errore
     */
    static void errorWindow(String Title, String Header, String Content){
        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setTitle(Title);
        alertBox.setHeaderText(Header);
        alertBox.setContentText(Content);
        alertBox.setResizable(false);
        alertBox.showAndWait();
    }
}
