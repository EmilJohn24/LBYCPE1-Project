package Server.Transaction;

import java.util.Date;
import java.util.Hashtable;

// Room File Format:
// Directory: Building/Floor/
// ~[X] [Y] [Vertex Info] [Starting Time HH:MM] [Minute Intervals for Reservations]
// [Interval Counts from Starting Time] [Username]
class RoomSlot {
    private Account client;
}

public class Room {
    private int capacity;
    private Hashtable<Date, RoomSlot> slots; //associates dates to rooms

}
