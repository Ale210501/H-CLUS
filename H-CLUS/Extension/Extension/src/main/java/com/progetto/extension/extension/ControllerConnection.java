package com.progetto.extension.extension;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;


/**
 * Classe che gestisce la connessione al server.
 */
public class ControllerConnection {
    /**
     * attributo FXML contenente l'indirizzo Ip fornito dall'utente
     */
    @FXML
    private TextField ipAddressFXML;
    /**
     * attributo FXML contenente il numero di porta fornita dall'utente
     */
    @FXML
    private TextField numberOfPortFXML;

    /**
     * Riceve in input l'indirizzo IP e il numero di porta e verifica
     * la validità di quest'ultimi per poi passare il controllo al SocketHandler per la connessione
     * e al ControllerMenu per visualizzare la finestra successiva
     * @param actionEvent
     * @throws IOException
     * @throws NumberFormatException
     */
    @FXML
    public void Connetti(ActionEvent actionEvent) throws IOException, NumberFormatException {
        try{
            String ipAddress = ipAddressFXML.getText();
            Integer numberOfPort = Integer.parseInt(numberOfPortFXML.getText());

            if (com.progetto.extension.extension.SocketHandler.checkSettings(ipAddress,numberOfPort))
                try {
                    SocketHandler socket = new SocketHandler(ipAddress, numberOfPort);
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    com.progetto.extension.extension.Utility.newWindow(stage, getClass(), "/com/progetto/extension/extension/HomePage.fxml", "H-Clus");
                } catch (ConnectException e) { //IOException
                    com.progetto.extension.extension.Utility.errorWindow("Connection Error", ""+e,
                            "Please check your internet connection and the information provided");
                }
        }catch (NumberFormatException e){
            com.progetto.extension.extension.Utility.errorWindow("Error",""+e, "Please check the values provided and try again");
        }
    }
}