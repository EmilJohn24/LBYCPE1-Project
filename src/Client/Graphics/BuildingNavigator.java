package Client.Graphics;

import Client.GraphicClientEndConnector;
import Server.Structures.Building;
import Server.Structures.Floor;

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
    }



    public void loadFloor(int floor){
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

    public void displayCurrentFloor(){
        roomDisplay.clearCanvas();
        Integer[] dateInfo = GraphicClientEndConnector.getDateInArrayForm();
        roomDisplay.colorizer(dateInfo[0], dateInfo[1], dateInfo[2], dateInfo[3], dateInfo[4]);
        roomDisplay.loadUpGraphics(currentFloor.getRooms());
    }


}
