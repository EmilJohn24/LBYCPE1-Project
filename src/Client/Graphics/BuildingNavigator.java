package Client.Graphics;

import Client.GraphicClientEndConnector;
import Server.Structures.Building;
import Server.Structures.Floor;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;

public class BuildingNavigator {
    private Floor currentFloor;
    private Building currentBuilding;
    private RoomDisplay roomDisplay;
    public BuildingNavigator(Building building){
        currentBuilding = building;
        GraphicClientEndConnector.setCurrentBuilding(currentBuilding);
        GraphicClientEndConnector.runPicker();
        //currentFloor = building.getFloors().get(0);
        //GraphicClientEndConnector.setCurrentFloor(currentFloor.getFloorCount());
        roomDisplay = new RoomDisplay();
        GraphicClientEndConnector.connectRoomGUI(roomDisplay);

    }



    public void loadFloor(int floor) throws TransformerException, IOException, SAXException {
        for (Floor f : currentBuilding.getFloors()){
            if (floor == f.getFloorCount()){
                currentFloor = f;
                displayCurrentFloor();
                return;
            }
        }
    }

    public void runner(){
        roomDisplay.start();
    }

    public void displayCurrentFloor() throws TransformerException, IOException, SAXException {
        roomDisplay.clearCanvas();
        Integer[] dateInfo = GraphicClientEndConnector.getDateInArrayForm();
        roomDisplay.loadUpGraphics(currentFloor.getRooms());
        roomDisplay.colorizer(dateInfo[0], dateInfo[1], dateInfo[2], dateInfo[3], dateInfo[4], GraphicClientEndConnector.getDuration());
        GraphicClientEndConnector.getPicker().setLocation(GraphicClientEndConnector.getRoomGUI().getX(), GraphicClientEndConnector.getRoomGUI().getBottomY() + 50);
        //50 is a manual adjustment dimension
        //consider making auto-adjustable in the future -Lopez


    }


}
