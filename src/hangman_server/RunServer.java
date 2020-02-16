package hangman_server;

import javax.xml.ws.Endpoint;

public class RunServer {
    public static void main(String[] args) {
        System.out.println("Starting server ... ");
        ConnectionHandler ch = new ConnectionHandler();
        System.out.println("Made class " + ch.toString());
        Endpoint.publish("http://[::]:9920/hangman", ch);
        System.out.println("SUCCESS");
    }
}