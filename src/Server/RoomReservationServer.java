package Server;

public class RoomReservationServer {
    public static void main(String[] args){
        Backend back = new Backend(4400, 10);
        back.takeConnections();
    }
}
