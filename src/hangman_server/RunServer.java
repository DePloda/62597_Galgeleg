package hangman_server;

import javax.xml.ws.Endpoint;

public class RunServer {

    public static void main(String[] args) {
        try {
            System.out.println("Starting server ... ");
            ConnectionHandler ch = new ConnectionHandler();
            System.out.println("Made class " + ch.toString());
            Endpoint.publish("http://[::]:9920/hangman", ch);
            System.out.println("[SERVER READY]");
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException occured: If the provided address URI is not usable in conjunction with the endpoint's binding.");
            iae.printStackTrace();
        } catch (IllegalStateException ise) {
            System.out.println("IllegalStateException occured: If the endpoint has been published already or it has been stopped.");
            ise.printStackTrace();
        } catch (Exception e) {
            System.out.println("Any exception occured");
            e.printStackTrace();
        }
    }

}
