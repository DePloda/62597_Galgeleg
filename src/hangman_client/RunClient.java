package hangman_client;

import hangman_common.IConnectionHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

// Class is set up to connect with SOAP
@SuppressWarnings("NonAsciiCharacters")
public class RunClient {

    private Scanner scanner;
    private IConnectionHandler server;

    public static void main(String[] args) {
        RunClient runClient = new RunClient();
        runClient.startConnection();
        runClient.awaitDecision();
    }

    private void awaitDecision() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        String input;
        while (true) {
            System.out.println(
                    "#######################################" +
                            "##" +
                            "##" +
                            "##" +
                            "##" +
                            "#######################################");

            input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            if (input.equals("play")) {
                gameLoop();
            }
        }

        scanner.close();
    }

    private void gameLoop() {

        server.startGame();
        while (!server.isGameOver()) {
            System.out.println("ORD DER SKAL GÆTTES: " + server.getVisibleWord());
            System.out.print("BRUGTE BOGSTAVER: ");
            ArrayList<String> letters = server.getUsedLetters();

            for (int i = 0; i < letters.size(); i++) {
                if (i == 0) {
                    System.out.print(letters.get(i));
                } else {
                    System.out.print(", " + letters.get(i));
                }
            }
            System.out.println();

            // When the user gives input
            System.out.print("Skriv bogstavet du vil gætte på: ");
            server.guessLetter(scanner.nextLine());
            System.out.println();
        }

        System.out.println("Ordet var: " + server.getWord());
        System.out.println("Spillet er slut");
        awaitDecision();

    }

    private void startConnection() {
        System.out.print("Connecting client to dist.saluton.dk ... ");
        try {
            URL url = new URL("http://s185120@dist.saluton.dk:9920/hangman?wsdl");
            QName qname = new QName("http://hangman_server/", "ConnectionHandlerService");
            Service service = Service.create(url, qname);
            server = service.getPort(IConnectionHandler.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("[CLIENT CONNECTED]");
    }

}
