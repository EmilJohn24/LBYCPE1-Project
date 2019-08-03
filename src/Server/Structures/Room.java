package Server.Structures;

import Server.Transaction.*;
import acm.graphics.GPolygon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;

// Room File Format:
// Directory: Building/Floor/
// ~[X] [Y] [Vertex Info] [Starting Time HH:MM] [Minute Intervals for Reservations]
// [Date] [Interval Counts from Starting Time] [Username]
class RoomSlot {
    private Account client;
}

public class Room {
    private int capacity;
    private Hashtable<Date, RoomSlot> slots; //associates dates to rooms
    private GPolygon graphic;
    private PrintWriter out;
    private BufferedReader in;

    public GPolygon getPolygonReference(){
        return graphic;
    }

    Room(int capacity, String infoFile) throws IOException {
        this.capacity = capacity;
        this.in = new BufferedReader(new FileReader(infoFile));
        this.out = new PrintWriter(infoFile);
    }
    //TODO: Load data from file to slots
    private void loadData(){
        return;
    }

}
