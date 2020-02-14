package hangman_common;

import hangman_server.Galgelogik;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@SuppressWarnings("NonAsciiCharacters")
@WebService
public interface IConnectionHandler {

    @WebMethod boolean login();

    @WebMethod void startGame();

    @WebMethod boolean guessLetter(String letter);

    @WebMethod String getVisibleWord();

    @WebMethod ArrayList<String> getUsedLetters();

    @WebMethod String getWord ();

}
