package com.progetto.extension.extension;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Classe che gestisce la connessione tra client e server.
 */
public class SocketHandler {
    /**
     * riferimento socket
     */
    private static Socket socket = null;
    /**
     * riferimento stream output
     */
    private static ObjectOutputStream out = null;
    /**
     * riferimento stream input
     */
    private static ObjectInputStream in = null;

    /**
     * Costruttore della classe, si occuppa di inizializzare la connessione
     * @param ip
     * @param numberOfPort
     * @throws IOException
     */
    SocketHandler(String ip, Integer numberOfPort) throws IOException {
        int port = numberOfPort.intValue();
        InetAddress addr = InetAddress.getByName(ip);
        socket = new Socket(addr,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * metodo per effettuare il controllo sulla correttezza dell'indirizzo IP fornito
     * @param ip
     * @return
     */
    public static boolean checkIp(String ip){
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    /**
     * metodo per effettuare il controllo sulla correttezza del numero di porta fornita
     * @param numberOfPort
     * @return
     */
    public static boolean checkPort(Integer numberOfPort){
        int port = numberOfPort.intValue();
        if((port>=1024)&&(port<=65535))
            return true;
        return false;
    }

    /**
     * metodo per effettuare il controllo sui parametri forniti per la connessione
     * @param ip
     * @param port
     * @return
     */
    static boolean checkSettings(String ip, Integer port){

        if(!ip.isEmpty() && !port.toString().isEmpty() && checkIp(ip) && checkPort(port))
            return true;

        if(ip.isEmpty())
            Utility.errorWindow("Error","The IP field is empty",
                    "Please insert a valide IP Address");

        if(port.toString().isEmpty())
            Utility.errorWindow("Error","The Port field is empty",
                    "Please insert a valide Port");

        if(!checkIp(ip))
            Utility.errorWindow("Error","the IP provided is incorrect",
                    "Please insert a valide IP Address");

        if(!checkPort(port))
            Utility.errorWindow("Error","the Port provided is incorrect",
                    "Please insert a valide Number of Port");

        return false;
    }
    /**
     * metodo che si occuppa della chiusura della socket
     */
    static void closeSocket(){
        if (socket != null && socket.isConnected()) {
            try {
                out.writeObject(-1);
                socket.close();
                out.close();
                in.close();
            } catch (IOException e) {
                Utility.errorWindow("Runtime Error!", "" + e, "Restart the program please");
                throw new RuntimeException(e);
            } catch (RuntimeException e) {
                Utility.errorWindow("Runtime Error!", "" + e, "Restart the program please");
            }
        }
    }
    /**
     * metodo che fornisce il riferimento al attributo di classe privato out
     * @return
     */
    static ObjectOutputStream getOut(){
        return out;
    }
    /**
     * metodo che fornisce il riferimento al attributo di classe privato in
     * @return
     */
    static ObjectInputStream getIn(){
        return in;
    }
}
