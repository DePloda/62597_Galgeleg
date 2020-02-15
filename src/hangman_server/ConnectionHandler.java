package hangman_server;

import hangman_common.IConnectionHandler;

import javax.jws.WebService;
import java.util.ArrayList;

@SuppressWarnings("NonAsciiCharacters")
@WebService(endpointInterface = "hangman_common.IConnectionHandler")
public class ConnectionHandler implements IConnectionHandler {

    private Galgelogik galgelogik;
    private ArrayList<Integer> connections = new ArrayList<>();

    @Override
    public boolean login(String username, String password) {
        // TODO: Implement this
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
    }

}
