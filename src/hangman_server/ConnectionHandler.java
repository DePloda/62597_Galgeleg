package hangman_server;

import javax.jws.WebService;
import java.util.ArrayList;

@SuppressWarnings("NonAsciiCharacters")
@WebService(endpointInterface = "hangman_common.IConnectionHandler")
public class ConnectionHandler {
    Galgelogik galgelogik;

    public boolean login() {
        // TODO: Implement this
        return false;
    }

    public void startGame() {
        galgelogik = new Galgelogik();
        galgelogik.nulstil();
    }

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

    public String getVisibleWord() {
        return galgelogik.getSynligtOrd();
    }

    public ArrayList<String> getUsedLetters() {
        return galgelogik.getBrugteBogstaver();
    }

    public String getWord () {
        return galgelogik.getOrdet();
    }

}
