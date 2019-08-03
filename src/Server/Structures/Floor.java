package Server.Structures;

import acm.graphics.GCanvas;

import java.util.ArrayList;

public class Floor {
    private ArrayList<Room> rooms;
    private GCanvas floor;

    public void addRoom(Room _newRoom){
        rooms.add(_newRoom);
        floor.add(_newRoom.getPolygonReference());
    }
}
