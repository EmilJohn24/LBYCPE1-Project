package Client;

import Client.Graphics.BuildingDisplay;
import Client.Picker.Picker;
import Server.Structures.Building;
import Server.Structures.Floor;
import Server.Structures.Room;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

//due to the deepness of the graphics package, this class has been implemented to connect up the deepest part of the stack to the client
//admittedly, this is due to lack of foresight in the architecture of the GUI-Client Interaction
public  class GraphicClientEndConnector {
    private static Client topClient;
    private static Building currentBuilding;
    private static int currentFloor;
    private static Room currentRoom;
    private static BuildingDisplay buildingGUI;
    private static Picker picker;
    public static void connectClient(Client c){
        topClient = c;
    }
    private static int month;
    private static int day;
    private static int year;
    private static int hour;
    private static int minute;

    public static Integer[] getDateInArrayForm(){
        Integer[] date = new Integer[5];
        date[0] = month;
        date[1] = day;
        date[2] = year;
        date[3] = hour;
        date[4] = minute;
        return date;
    }


    public static void changeTime(int month, int day, int year, int hour, int minute){
        GraphicClientEndConnector.month = month;
        GraphicClientEndConnector.day = day;
        GraphicClientEndConnector.year = year;
        GraphicClientEndConnector.hour = hour;
        GraphicClientEndConnector.minute = minute;
    }



    public static void processReservation(){
        String response = topClient.sendReservationRequest(currentBuilding.getName(), currentFloor, currentRoom.getName(), month, day, year, hour, minute);
        switch(response){
            case "RESERVATION_COMPLETE":
                JOptionPane.showMessageDialog(null, "Reservation successful");
                RoomReservationClient.main(null);
            case "SLOT_NOT_FOUND":
                JOptionPane.showMessageDialog(null, "Please select an existing slot");
                return;
            case "ROOM_NOT_AVAILABLE":
                JOptionPane.showMessageDialog(null, "This room is unavailable");
                return;



        }
    }
    public static void loadUpBuildingGraphics(ArrayList<Building> buildings){
        buildingGUI.loadUpGraphics(buildings);
        buildingGUI.start();
    }

    public static void putFloorsInPicker(){
        for (Floor f : currentBuilding.getFloors()){
            picker.addChoice(f.getFloorCount());
        }
    }

    public static void runPicker(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                picker.setVisible(true);
                picker.clear();
                if (currentBuilding != null){
                    putFloorsInPicker();
                }
            };
        });


    }

    public static void runClient(){
        try {
            topClient.loadBuildingGUI();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Client getClientReference(){
        return topClient;
    }

    public static void displayNewFloor(int floor){
        buildingGUI.getNav().loadFloor(floor);
    }


    static{
        buildingGUI = new BuildingDisplay();
        picker = new Picker();
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
        GraphicClientEndConnector.currentFloor = currentFloor;
        displayNewFloor(currentFloor);
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(Room currentRoom) {
        GraphicClientEndConnector.currentRoom = currentRoom;
    }


}
