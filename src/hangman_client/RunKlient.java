package hangman_client;

import hangman_common.IConnectionHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("NonAsciiCharacters")
public class RunKlient {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Staring client ... ");
        URL url = new URL("http://javabog.dk:9920/hangman?wsdl");
        QName qname = new QName("http://hangman_server/", "ConnectionHandlerService");
        Service service = Service.create(url, qname);
        IConnectionHandler server = service.getPort(IConnectionHandler.class);
        System.out.println("[CLIENT READY]");

        System.out.println("Velkommen til dette galgelegspil");

        /*
        // TODO: Userautorisation
        System.out.println("Du bedes logge ind via javabog.");
        System.out.print("Brugernavn: ");
        scanner.nextLine();
        System.out.println();
        System.out.print("Kodeord: ");
        scanner.nextLine();
        System.out.println();
        System.out.println("Du er nu logget ind som: ");
        */

        System.out.println("Du kan nu spille galgelegsspillet." +
                "\nØnsker du at begynde et nyt spil? y/n");
        if (scanner.nextLine().toLowerCase().equals("y")) {
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
                System.out.print("Skriv et bogstav for at gætte: ");
                server.guessLetter(scanner.nextLine());
                System.out.println();
            }

            System.out.println("Ordet var: " + server.getWord());
        } else {
            scanner.close();
            System.exit(0);
        }

        scanner.close();
    }

}
