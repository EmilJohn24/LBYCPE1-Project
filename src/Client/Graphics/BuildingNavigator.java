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
        currentFloor = building.getFloors().get(0);
        GraphicClientEndConnector.setCurrentFloor(currentFloor.getFloorCount());
        roomDisplay = new RoomDisplay();
    }



    public void loadFloor(int floor){
        for (Floor f : currentBuilding.getFloors()){
            if (floor == f.getFloorCount()){
                currentFloor = f;
                return;
            }
        }
    }

    public void displayCurrentFloor(){
        roomDisplay.loadUpGraphics(currentFloor.getRooms());
        roomDisplay.start();
    }


}
