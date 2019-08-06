package Server.Structures;

import acm.graphics.GCanvas;

import java.util.ArrayList;

public class Floor {
    private ArrayList<Room> rooms;
    private int floorCount;
    private GCanvas floor;
    private int width;
    private int height;

    Floor(int floorCount, int width, int height){
        this.floorCount = floorCount;
        this.width = width;
        this.height = height;
        floor = new GCanvas();
        floor.setSize(width, height);
    }
    public void addRoom(Room newRoom){
        rooms.add(newRoom);
        floor.add(newRoom.getGraphic());
    }

    public void createRoom(String name, String description, int capacity,
                           double top, double left, double width, double height){
        addRoom(new Room(name, description, capacity,
                        top, left, width, height));
    }



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        floor.setSize(width, floor.getHeight());
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        floor.setSize(floor.getWidth(), height);
        this.height = height;
    }
}
