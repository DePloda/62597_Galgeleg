package hangman_server;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import hangman_common.IConnectionHandler;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("NonAsciiCharacters")
@WebService(endpointInterface = "hangman_common.IConnectionHandler")
public class ConnectionHandler implements IConnectionHandler {

    private Galgelogik galgelogik;
    private ArrayList<Integer> connections = new ArrayList<>();

    @Override
    public boolean login(int clientID, String username, String password) {

        try {
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);
            Brugeradmin ba = service.getPort(Brugeradmin.class);

            Bruger bruger = ba.hentBruger(username, password);
            if (bruger.brugernavn.equals(username) && bruger.adgangskode.equals(password)) {
                System.out.println("Client with id " + clientID + " successfully logged in");
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            System.out.println("Client with id " + clientID + " failed to login");
            return false;
        }
        return false;
    }

    @Override
    public void startGame() {
        galgelogik = new Galgelogik();
        galgelogik.nulstil();
    }

    @Override
    public boolean isGameOver() {
        return galgelogik.erSpilletSlut();
    }

    @Override
    public boolean guessLetter(String letter) {
        if (letter.isEmpty()) {
            return false;
        }
        if (letter.length() > 1) {
            return false;
        }
        galgelogik.gætBogstav(letter);
        return true;
    }

    @Override
    public String getVisibleWord() {
        return galgelogik.getSynligtOrd();
    }

    @Override
    public ArrayList<String> getUsedLetters() {
        return galgelogik.getBrugteBogstaver();
    }

    @Override
    public String getWord () {
        return galgelogik.getOrdet();
    }

    @Override
    public int informConnect() {
        boolean usedID;
        int randomID;
        do {
            usedID = false;
            randomID = (int)(Math.random() * 10000);
            for (int ids : connections) {
                if (ids == randomID) {
                    usedID = true;
                }
            }
        } while (usedID);
        connections.add(randomID);
        System.out.println("Client with id " + randomID + " has connected.");
        return randomID;
    }

    @Override
    public void informDisconnect(int id) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i) == id) {
                connections.remove(i);
                break;
            }
        }
        System.out.println("Client with id " + id + " disconnected.");

        if (connections.size() > 0) {
            System.out.println("Active connections are: ");
            for (int connection : connections) {
                System.out.println(connection);
            }
            System.out.println();
        } else {
            System.out.println("No active connections");
        }
    }

}
