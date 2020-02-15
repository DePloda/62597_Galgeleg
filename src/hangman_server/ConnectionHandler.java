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
import java.util.HashMap;

@SuppressWarnings("NonAsciiCharacters")
@WebService(endpointInterface = "hangman_common.IConnectionHandler")
public class ConnectionHandler implements IConnectionHandler {

    private Galgelogik galgelogik;
    private HashMap<Integer, String> connections = new HashMap<>();

    @Override
    public boolean login(int clientID, String username, String password) {

        try {
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);
            Brugeradmin ba = service.getPort(Brugeradmin.class);

            Bruger bruger = ba.hentBruger(username, password);
            if (bruger.brugernavn.equals(username) && bruger.adgangskode.equals(password)) {
                System.out.println("Client with id " + clientID + " successfully logged in as " + username);
                connections.put(clientID, username);
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
            if (connections.containsKey(randomID)) {
                usedID = true;
            }
        } while (usedID);
        connections.put(randomID, "unknown");
        System.out.println("Client with id " + randomID + " has connected.");

        if (connections.size() > 0) {
            System.out.println("Active connections are: ");
            for (int clientid : connections.keySet()){
                System.out.println("id: " + clientid + " username: " + connections.get(clientid));
            }
            System.out.println();
        } else {
            System.out.println("No active connections");
        }

        return randomID;
    }

    @Override
    public void informDisconnect(int id) {
        connections.remove(id);
        System.out.println("Client with id " + id + " disconnected.");
    }

}
