package com.compito;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;// stringa ricevuta dal client
    String stringaDaInviare = "";
    BufferedReader inDalClient;// stream di input
    DataOutputStream outVersoClient;// Stream di output
    ArrayList<String> lista;
    public Server() {
        lista = new ArrayList<>();
    }
    public Socket attendi() {
        try {
            System.out.println("Il SERVER è in esecuzione");
            server = new ServerSocket(5000); // creo un server sulla porta 5000
            client = server.accept(); // rimane in attesa di un client
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
        return client;
    }
    public void comunica() {
        try {
            System.out.println("Benvenuto, connessione effettuata");
            System.out.println("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate");
            for (;;) {
                stringaRicevuta = inDalClient.readLine();
                if (stringaRicevuta.equals("LISTA")) {
                    for (int i = 0; i < lista.size(); i++) {
                        stringaDaInviare += lista.get(i) + " ";
                    }
                    outVersoClient.writeBytes(stringaDaInviare + "\n");
                    stringaDaInviare = "";
                } else {
                    lista.add(stringaRicevuta);
                    System.out.println("Nota salvata");
                    System.out.println("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate");
                }
            }
        } catch (Exception e) {
        }
    }
    public static void main(String[] args) {

        Server server = new Server();
        server.attendi();
        server.comunica();
    }
}
