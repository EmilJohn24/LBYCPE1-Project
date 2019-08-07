package Server.Structures;

import Server.Transaction.*;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

// Room File Format:
// Directory: Building/Floor/
// ~[X] [Y] [Vertex Info] [Starting Time HH:MM] [Minute Intervals for Reservations]
// [Date] [Interval Counts from Starting Time] [Username]
class RoomSlot {
    public RoomSlot(){
        client = null;
    }

    public RoomSlot(Account client){
        this.client = client;
    }

    protected Account getClient(){
        return this.client;
    }


    public boolean isEmpty(){
        return getClient() == null;
    }
    private Account client;
}


public class Room implements ResizableStruct{
    private int capacity;
    private Hashtable<Calendar, RoomSlot> slots; //associates dates to rooms
    private GRect graphic;
    private String name;
    private String description;
    private double top;
    private double left;
    private double width;
    private double height;

    public GRect getGraphic(){
        return graphic;
    }

    Room(String name, String description, int capacity, double top, double left, double width, double height){
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.name = name;
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.slots = new Hashtable<>();
        this.graphic = new GRect(left, top, width, height);

    }

    Room(){
        this.name = new String();
        this.capacity = 0;
        this.description = new String();
        this.top = 0;
        this.left = 0;
        this.width = 0;
        this.height = 0;
        this.slots = new Hashtable<>();
        this.graphic = new GRect(0, 0 ,0, 0);
        return;
    }

    public static Calendar turnToTime(int month, int day, int year, int hour, int minute){
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day, hour, minute);
        return newDate;
    }

    public void addEmptySlot(int month, int day, int year, int hour, int minute){
        Calendar newDate = Room.turnToTime(month, day, year, hour, minute);
        RoomSlot newSlot = new RoomSlot();
        slots.putIfAbsent(newDate, newSlot); //TODO: Note possible compatibility issues. Look up later
    }

    public void fillSlot(Account user, int month, int day, int year, int hour, int minute){
        Calendar newDate = Room.turnToTime(month, day, year, hour, minute);
        slots.put(newDate, new RoomSlot(user));

    }


    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        graphic.setSize(graphic.getWidth(), height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        graphic.setSize(width, graphic.getHeight());
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
        graphic.setX(left);
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
        graphic.setY(top);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
