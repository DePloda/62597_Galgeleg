package hangman_server;

import javax.xml.ws.Endpoint;

public class RunServer {

    public static void main(String[] args) {

        System.out.print("Starting server ... ");
        ConnectionHandler ch = new ConnectionHandler();
        Endpoint.publish("http://[::]:9905/hangman", ch);
        System.out.println("[SERVER READY]");

    }

}
