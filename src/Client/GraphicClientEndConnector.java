package Client;

import Client.Graphics.BuildingDisplay;
import Server.Structures.Building;
import Server.Structures.Floor;
import Server.Structures.Room;

import java.util.ArrayList;
import java.util.Calendar;

//due to the deepness of the graphics package, this class has been implemented to connect up the deepest part of the stack to the client
//admittedly, this is due to lack of foresight in the architecture of the GUI-Client Interaction
public  class GraphicClientEndConnector {
    private static Client topClient;
    private static Building currentBuilding;
    private static int currentFloor;
    private static Room currentRoom;
    private static Calendar currentDate;
    private static BuildingDisplay buildingGUI;

    public static void connectClient(Client c){
        topClient = c;
    }

    public static void loadUpBuildingGraphics(ArrayList<Building> buildings){
        buildingGUI.loadUpGraphics(buildings);
        buildingGUI.start();
    }


    public static Client getClientReference(){
        return topClient;
    }

    private static void displayNewFloor(int floor){
        buildingGUI.getNav().loadFloor(floor);
    }


    static{
        buildingGUI = new BuildingDisplay();
    }

    public static Building getCurrentBuilding() {
        return currentBuilding;
    }

    public static void setCurrentBuilding(Building currentBuilding) {
        GraphicClientEndConnector.currentBuilding = currentBuilding;
    }

    public static int getCurrentFloor() {
        return currentFloor;
    }

    public static void setCurrentFloor(int currentFloor) {
        displayNewFloor(currentFloor);
        GraphicClientEndConnector.currentFloor = currentFloor;
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(Room currentRoom) {
        GraphicClientEndConnector.currentRoom = currentRoom;
    }

    public static Calendar getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(Calendar currentDate) {
        GraphicClientEndConnector.currentDate = currentDate;
    }
}
