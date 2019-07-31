import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;


class RoomSlot {
    int student_count;

}

public class Room {
    private int capacity;
    private Hashtable<Date, RoomSlot> slots; //associates dates to rooms

}
