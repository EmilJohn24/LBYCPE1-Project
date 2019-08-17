package Server.Structures;

import Client.Graphics.GenericGraphicsWindow;
import acm.graphics.GRect;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class StructureEditor extends GenericGraphicsWindow<Room> {
    private StructureHandler handlerRef;
    private Floor currentFloor;
    private Element currentFloorElement;
    private NodeList floorElements;
    private Room pinnedRoom;

    @Override
    public void highLevelClickHandler(Room clickedObject) {
        pinnedRoom = clickedObject;
    }

    public StructureEditor(StructureHandler handler){
        this.handlerRef = handler;
    }

    public void updateCorrespondingElementCoord(int x, int y){
        pinnedRoom.setLeft(x - pinnedRoom.getWidth() / 2); //places the building's center on the mouse
        pinnedRoom.setTop(y - pinnedRoom.getHeight() / 2);
        for (int i = 0; i != floorElements.getLength(); i++){
            Element updatedElement = (Element) floorElements.item(i);
            updatedElement.setAttribute("top", String.valueOf(y));
            updatedElement.setAttribute("left", String.valueOf(x));
            try {
                handlerRef.updateFile();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (pinnedRoom != null){
            int x = e.getX();
            int y = e.getY();
            updateCorrespondingElementCoord(x, y);

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

    }


    public void openFloor(String building, int floor){
        ArrayList<Building> buildings = handlerRef.parse();
        currentFloorElement = handlerRef.lookup(building, floor);
        floorElements = currentFloorElement.getElementsByTagName(StructureHandler.roomIndicator);

        for (Building b : buildings){
            if (b.getName() == building){
                for (Floor f : b.getFloors()){
                    if (f.getFloorCount() == floor){
                        currentFloor = f;
                        loadUpGraphics(currentFloor.getRooms());
                        break;
                    }
                }
                break;
            }
        }
    }
}
