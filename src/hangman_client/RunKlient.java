package hangman_client;

import hangman_common.IConnectionHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

@SuppressWarnings("NonAsciiCharacters")
public class RunKlient {

    public static void main(String[] args) throws Exception {

        System.out.print("Staring client ... ");
        URL url = new URL("http://localhost:9905/hangman?wsdl");
        QName qname = new QName("http://hangman_server/", "ConnectionHandlerService");
        Service service = Service.create(url, qname);
        IConnectionHandler server = service.getPort(IConnectionHandler.class);
        System.out.println("[CLIENT READY]");

        server.startGame();
        System.out.println(server.getVisibleWord());

    }

}
