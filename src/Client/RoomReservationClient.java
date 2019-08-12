package Client;

import java.io.IOException;

public class RoomReservationClient {
    public static void main(String[] args) {
        try {
            Client client = new Client("127.0.0.1", 4400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
