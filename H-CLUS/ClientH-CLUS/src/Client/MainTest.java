package Client;

import keyboardinput.Keyboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Classe MainTest che gestisce la comunicazione lato client con un server.
 * Consente di caricare un dendrogramma da file o apprendere un dendrogramma da
 * un database.
 */
public class MainTest {

    /**
     * @param args argomenti , linea di comando , non utilizzati.
     */
    private ObjectOutputStream out;
    private ObjectInputStream in ; // stream con richieste del client

    /**
     * Costruttore che inizializza la connessione con il server.
     *
     * @param ip   l'indirizzo IP del server.
     * @param port la porta su cui connettersi al server.
     * @throws IOException se si verifica un errore di I/O durante la connessione.
     */
    public MainTest(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
    }
    /**
     * Metodo che visualizza un menu per l'utente e legge l'opzione scelta.
     *
     * @return l'opzione scelta dall'utente.
     */
    private int menu(){
        int answer;
        System.out.println("Scegli una opzione");
        do{
            System.out.println("(1) Carica Dendrogramma da File");
            System.out.println("(2) Apprendi Dendrogramma da Database");
            System.out.print("Risposta:");
            answer=Keyboard.readInt();
        }
        while(answer<=0 || answer>2);
        return answer;

    }
    /**
     * Metodo per caricare i dati sul server.
     *
     * @throws IOException            se si verifica un errore di I/O.
     * @throws ClassNotFoundException se si verifica un errore di deserializzazione.
     */
    private void loadDataOnServer() throws IOException, ClassNotFoundException {
        boolean flag=false;
        do {
            System.out.println("Nome tabella:");
            String tableName = Keyboard.readString();
            out.writeObject(0);
            out.writeObject(tableName);
            String risposta = (String) (in.readObject());
            if (risposta.equals("OK"))
                flag = true;
            else System.out.println(risposta);

        }while(flag==false);
    }
    /**
     * Metodo per caricare un dendrogramma da un file sul server.
     *
     * @throws IOException            se si verifica un errore di I/O.
     * @throws ClassNotFoundException se si verifica un errore di deserializzazione.
     */
    private void loadDedrogramFromFileOnServer() throws IOException, ClassNotFoundException {
        System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
        String fileName=Keyboard.readString();

        out.writeObject(2);
        out.writeObject(fileName);
        String risposta= (String) (in.readObject());
        if(risposta.equals("OK"))
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }
    /**
     * Metodo per apprendere un dendrogramma sul server.
     *
     * @throws IOException            se si verifica un errore di I/O.
     * @throws ClassNotFoundException se si verifica un errore di deserializzazione.
     */
    private void mineDedrogramOnServer() throws IOException, ClassNotFoundException {


        out.writeObject(1);
        System.out.println("Introdurre la profondita'  del dendrogramma");
        int depth=Keyboard.readInt();
        out.writeObject(depth);
        int dType=-1;
        do {
            System.out.println("Distanza: single-link (1), average-link (2):");
            dType=Keyboard.readInt();
        }while (dType<=0 || dType>2);
        out.writeObject(dType);

        String risposta= (String) (in.readObject());
        if(risposta.equals("OK")) {
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
            System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
            String fileName=Keyboard.readString();
            out.writeObject(fileName);
        }
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }
    /**
     * Metodo Main lato Client per avviare la comunicazione con il Server.
     *
     * @param args, parametri riga di coomando: indirizzo IP e porta del server.
     */
    public static void main(String[] args) {
        String ip=args[0];
        int port= Integer.valueOf(args[1]).intValue();
        MainTest main=null;
        try{
            main=new MainTest(ip,port);

            main.loadDataOnServer();
            int scelta=main.menu();
            if(scelta==1)
                main.loadDedrogramFromFileOnServer();
            else
                main.mineDedrogramOnServer();


        }
        catch (IOException |ClassNotFoundException  e){
            System.out.println(e);
            return;
        }
    }

}