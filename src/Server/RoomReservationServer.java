package Server;

import Server.Transaction.Manager;

public class RoomReservationServer {
    public static void main(String[] args){
        Manager.runGUI();
        Backend back = new Backend(4400, 10);
        back.takeConnections();
    }
}
