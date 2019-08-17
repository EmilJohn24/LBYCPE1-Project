package Client.Graphics;

import Client.GraphicClientEndConnector;
import Server.Structures.Room;
import Server.Structures.RoomSlot;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;

public class RoomDisplay extends GenericGraphicsWindow<Room> {

    public void highLevelClickHandler(Room clickedRoom) {
        GraphicClientEndConnector.setCurrentRoom(clickedRoom);
        GraphicClientEndConnector.processReservation();
    }

    public void colorizer(int month, int day, int year, int hour, int minute, int length) throws TransformerException, IOException, SAXException {
        ArrayList<GLabel> safeList = new ArrayList<>();
        System.out.println("Coloring in stuff...");
        Iterator<GObject> runner = iterator();
        GObject container = null;
        Calendar compare = Calendar.getInstance();
        compare.set(year, month, day, hour, minute);
        for (int i = 0; i != getElementCount(); i++) {
            container = this.getElement(i);
            //if (container == null) System.out.println("Wait what");
            if (container instanceof GLabel && !safeList.contains(container)){
                GLabel fakeText = (GLabel) container;
                if (fakeText.getText() == "NOT AVAILABLE" || fakeText.getText() == "AVAILABLE") {
                    remove(container);
                }
            }
            if (container instanceof GRect) {
                Room currentRoom = getStructWithGRect((GRect) container);
                System.out.println(currentRoom.getName());
                double x = currentRoom.getGraphic().getX();
                double y = currentRoom.getGraphic().getY() + 20;
                currentRoom.getGraphic().setFilled(true);
                if (!GraphicClientEndConnector.checkIfAvailable(month, day, year, hour, minute, length, currentRoom.getName())) {
                    GLabel notAvail = new GLabel("NOT AVAILABLE", x, y);
                    safeList.add(notAvail);
                    addToGraphics(notAvail);
                    currentRoom.getGraphic().setFillColor(Color.GRAY);
                } else {
                    GLabel avail = new GLabel("AVAILABLE", x, y);
                    safeList.add(avail);
                    addToGraphics(avail);
                    currentRoom.getGraphic().setFillColor(Color.GREEN);
                }
            }
            repaint();
        }
    }
}

