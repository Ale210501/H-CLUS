package com.progetto.extension.extension;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.URL;

/**
 * Classe che gestisce gli input e output dell'applicazione.
 */
public class ControllerIO {
    /**
     * contiene il nome di tabella fornito in input dall'utente
     */
    @FXML
    private TextField tableName;
    /**
     * contiene la profondità da utilizzare fornita dall'utente in input
     */
    @FXML
    private TextField depth;
    /**
     * bottone per utilizzare la distanza single-link
     */
    @FXML
    private Button singleDistance;
    /**
     * bottone per utilizzare la distanza average-link
     */
    @FXML
    private Button averageDistance;
    /**
     * contiene il nome del file fornito dall'utente in input
     */
    @FXML
    private TextField fileName;
    /**
     * contiene l'output dell'alaborazione effettuata dal server
     */
    @FXML
    private TextArea output;
    /**
     * bottone per effettuare l'operazione di salvataggio
     */
    @FXML
    private Button salva;
    /**
     * contiene lo stream di input
     */
    private ObjectInputStream in = com.progetto.extension.extension.SocketHandler.getIn();
    /**
     * contiene lo stream di output
     */
    private ObjectOutputStream out = com.progetto.extension.extension.SocketHandler.getOut();
    /**
     * metodo per effettuare l'operazione di ritorno al menu principale
     * @param mouseEvent
     * @throws IOException
     */
    public void goBack(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        com.progetto.extension.extension.Utility.newWindow(stage, getClass(), "/com/progetto/extension/extension/HomePage.fxml", "H-Clus");
    }
    /**
     * imposta la schermata di output con il risultato fornito dal server
     * @param text
     */
    void setOutputText(String text){
        output.setEditable(false);
        output.setText(text);
    }
    /**
     * metodo che effettua l'operazione di lettura da file
     */
    public void learningFromFile(){
        String text;
        try {
            String fileName = this.fileName.getText();
            out.writeObject(2);
            try {
                out.writeObject(fileName);
                String result = (String) in.readObject();
                if (result.equals("OK")) {
                    text = (String) in.readObject();
                    setOutputText(text);
                } else throw new ServerException(result);
            }  catch (ServerException e) {
                com.progetto.extension.extension.Utility.errorWindow("Error", "" + e, "Please check the values provided and try again");
            }
        } catch (SocketException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (FileNotFoundException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (IOException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ClassNotFoundException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (NumberFormatException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", "" + e, "Please check the values provided and try again");
        }
    }
    /**
     * metodo per caricare le tabelle dal DB
     * @param actionEvent
     */
    public void storeTableFromDb(ActionEvent actionEvent) {
        try{
            String tableName = this.tableName.getText();
            out.writeObject(0);
            out.writeObject(tableName);
            String result = (String) in.readObject();
            if(!result.equals("OK"))
                throw new ServerException(result);
            else {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                com.progetto.extension.extension.Utility.newWindow(stage, getClass(), "/com/progetto/extension/extension/Scoperta.fxml", "H-Clus");
            }
        } catch (IOException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ServerException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ClassNotFoundException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        }
    }
    /**
     * metodo per la scoperta di un dendrogramma di cluster mediante le tabelle DB
     * utilizzando la distanza single-link
     */
    public void learningFromDbTableSD() {
        singleDistance.setDisable(true);
        String text;
        try{
            singleDistance.setDisable(false);
            int depth = Integer.parseInt(this.depth.getText());
            int distanceType = 1;
            out.writeObject(1);
            out.writeObject(depth);
            out.writeObject(distanceType);
            String result = (String)in.readObject();
            if(result.equals("OK")){
                text = ("Profondità: "+depth+"\n");
                text += ("Distanza utilizzata: single-link \n");
                text += (String)in.readObject();
            }else throw new ServerException(result);
            URL path = getClass().getResource("/com/progetto/extension/extension/Output.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(path);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = loader.load(path.openStream());
            ControllerIO contr = loader.getController();
            contr.setOutputText(text);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("H-Clus");
            stage.getIcons().add(new Image("file:src/com/progetto/extension/extension//icon.png"));
            stage.setX(120);
            stage.setY(430);
            stage.show();
        } catch (IOException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ServerException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ClassNotFoundException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (NumberFormatException e){
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        }
    }
    /**
     * metodo per la scoperta di un dendrogramma di cluster mediante le tabelle DB
     * utilizzando la distanza average-link
     */
    public void learningFromDbTableAD() {
        averageDistance.setDisable(true);
        String text;
        try{
            averageDistance.setDisable(false);
            int depth = Integer.parseInt(this.depth.getText());
            int distanceType = 2;
            out.writeObject(1);
            out.writeObject(depth);
            out.writeObject(distanceType);
            String result = (String)in.readObject();
            if(result.equals("OK")){
                text = ("Profondità: "+depth+"\n");
                text += ("Distanza utilizzata: average-link \n");
                text += (String)in.readObject();
            }else throw new ServerException(result);
            URL path = getClass().getResource("/com/progetto/extension/extension/Output.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(path);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = loader.load(path.openStream());
            ControllerIO contr = loader.getController();
            contr.setOutputText(text);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("H-Clus");
            stage.getIcons().add(new Image("file:src/resources/icon.png"));
            stage.setX(120);
            stage.setY(430);
            stage.show();
        } catch (IOException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ServerException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (ClassNotFoundException e) {
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        } catch (NumberFormatException e){
            com.progetto.extension.extension.Utility.errorWindow("Error", ""+ e, "Please check the values provided and try again");
        }
    }
    /**
     * metodo che si occupa dell'opzione di salvataggio su file il dendrogramma di cluster scoperti
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void storeClusterInFile() throws SocketException, ServerException,IOException,ClassNotFoundException{
        salva.setDisable(true);
        String fileName = this.fileName.getText();
        out.writeObject(fileName);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);
    }
}


