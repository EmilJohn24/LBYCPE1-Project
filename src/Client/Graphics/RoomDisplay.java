package Client.Graphics;

import Client.GraphicClientEndConnector;
import Server.Structures.Room;

public class RoomDisplay extends GenericGraphicsWindow<Room> {
    public void highLevelClickHandler(Room clickedRoom){
        GraphicClientEndConnector.setCurrentRoom(clickedRoom);
    }
}
