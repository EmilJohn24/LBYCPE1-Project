package Client;

import Client.Login.Login;

import java.io.IOException;

public class RoomReservationClient {
    public static void main(String[] args) {
        Client clientHolder = null;
        try {
            clientHolder = new Client("127.0.0.1", 4400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicClientEndConnector.connectClient(clientHolder);
        Login.runner(args);

    }
}
