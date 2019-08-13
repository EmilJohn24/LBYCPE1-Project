package Client.Graphics;

import Client.GraphicClientEndConnector;
import Server.Structures.Room;
import Server.Structures.RoomSlot;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;

public class RoomDisplay extends GenericGraphicsWindow<Room> {
    public void highLevelClickHandler(Room clickedRoom){
        GraphicClientEndConnector.setCurrentRoom(clickedRoom);
        GraphicClientEndConnector.processReservation();
    }

    public void colorizer(int month, int day, int year, int hour, int minute){
        Iterator<GObject> runner = iterator();
        GObject container = null;
        Calendar compare = Calendar.getInstance();
        compare.set(year, month, day, hour, minute);
        for (int i = 0; i != getElementCount(); i++)
            container = this.getElement(i);
            if (container instanceof GRect){
                Room currentRoom = getStructWithGRect((GRect) container);
                System.out.println(currentRoom.getName());
                Hashtable<Calendar, RoomSlot> roomSlots = currentRoom.getSlots();
                double x = currentRoom.getGraphic().getX();
                double y = currentRoom.getGraphic().getY();

                if(!roomSlots.containsKey(compare)){
                    GraphicClientEndConnector.addGraphic(new GLabel("NOT AVAILABLE", x, y));
                    currentRoom.getGraphic().setFillColor(Color.GRAY);
                                    }
                else if (roomSlots.get(compare).isEmpty()) {
                    GraphicClientEndConnector.addGraphic(new GLabel("AVAILABLE", x, y));
                    currentRoom.getGraphic().setFillColor(Color.GREEN);
                }
                else{
                    GraphicClientEndConnector.addGraphic(new GLabel("TAKEN", x, y));
                    currentRoom.getGraphic().setFillColor(Color.RED);
                }
                currentRoom.getGraphic().setFilled(true);
            }
        }
    }

