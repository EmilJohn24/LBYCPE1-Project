package Client;

import Client.Graphics.BuildingDisplay;
import Client.Graphics.RoomDisplay;
import Client.Picker.Picker;
import Client.Reason.Reason;
import Client.Receipt.DRRMSReceipt;
import Server.Structures.Building;
import Server.Structures.Floor;
import Server.Structures.Room;
import Server.Structures.StructureHandler;
import acm.graphics.GObject;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

//due to the deepness of the graphics package, this class has been implemented to connect up the deepest part of the stack to the client
//admittedly, this is due to lack of foresight in the architecture of the GUI-Client Interaction
public  class GraphicClientEndConnector {
    private static Client topClient;
    private static String topClientUsername;
    private static Building currentBuilding;
    private static int currentFloor;
    private static Room currentRoom;
    private static BuildingDisplay buildingGUI;
    private static Picker picker;
    private static RoomDisplay roomGUI;
    public static void connectClient(Client c){
        topClient = c;
    }

    public static void setTopClientUsername(){
        if (topClient.getSessionID() != 0) topClientUsername = topClient.requestUsername();
    }
    private static int month;
    private static int day;
    private static int year;
    private static int hour;
    private static int minute;
    private static int duration;
    private static DRRMSReceipt receipt;

    public static Integer[] getDateInArrayForm(){
        Integer[] date = new Integer[5];
        date[0] = month;
        date[1] = day;
        date[2] = year;
        date[3] = hour;
        date[4] = minute;
        return date;
    }

    public static Picker getPicker(){
        return picker;
    }
    public static int getDuration(){
        return duration;
    }

    public static void connectRoomGUI(RoomDisplay roomDisplay){
        roomGUI = roomDisplay;
    }

    public static RoomDisplay getRoomGUI(){
        return roomGUI;
    }

    public static void changeTime(int month, int day, int year, int hour, int minute, int duration) {
        GraphicClientEndConnector.month = month;
        GraphicClientEndConnector.day = day;
        GraphicClientEndConnector.year = year;
        GraphicClientEndConnector.hour = hour;
        GraphicClientEndConnector.minute = minute;
        GraphicClientEndConnector.duration = duration;

        try {
            getRoomGUI().colorizer(month, day, year, hour, minute, duration);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
    private static String reason;
    private static Reason reasonForm;
    public static void waitForReason(){
        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                reasonForm.setVisible(true);
//            }
//        });
            EventQueue.invokeLater(()-> {
                        reasonForm = new Reason();
                        reasonForm.setVisible(true);

                    });

        //reasonForm.dispose();
        }


    public static void processReservation(){
        //
        reason = reasonForm.getReason();
        reasonForm.stop();
        reason = reason.replace(" ", "+");
        picker.stop();
        String response = topClient.sendReservationRequest(currentBuilding.getName(), currentFloor, currentRoom.getName(), month, day, year, hour, minute, duration, reason);
        System.out.println(response);
        switch(response){
            case "RESERVATION_COMPLETE":
                loadReceipt();
                JOptionPane.showMessageDialog(null, "Reservation successful");
                //RoomReservationClient.main(null);
                //System.exit(0);
                while(true);
            case "SLOT_NOT_FOUND":
                JOptionPane.showMessageDialog(null, "Please select an existing slot");
                return;
            case "ROOM_NOT_AVAILABLE":
                JOptionPane.showMessageDialog(null, "This room is unavailable");
                return;
        }



    }

    public static void addGraphic(GObject g){
        buildingGUI.add(g);
        buildingGUI.repaint();
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
            }
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

    public static void loadReceipt(){
        LocalDateTime dateAndTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:ss");
        String datetime = dateAndTime.format(formatter);
        int fakeID = Math.abs(topClient.getSessionID());
        java.awt.EventQueue.invokeLater(() -> {
            receipt.displayinformation(getUsername(), currentRoom.getName(), datetime, reason.replace("+", " "), String.valueOf(fakeID));
            receipt.setVisible(true);
        });

    }

    public static Client getClientReference(){
        return topClient;
    }

    public static void displayNewFloor(int floor) throws TransformerException, IOException, SAXException {
        buildingGUI.getNav().loadFloor(floor);
    }


    static{
        buildingGUI = new BuildingDisplay();
        picker = new Picker();
        receipt = new DRRMSReceipt();

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

    public static void setCurrentFloor(int currentFloor) throws TransformerException, IOException, SAXException {
        GraphicClientEndConnector.currentFloor = currentFloor;
        displayNewFloor(currentFloor);
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    public static String getUsername(){
        return topClient.requestUsername();
    }
    public static boolean checkIfAvailable(int month, int day, int year, int hour, int minute, int length, String roomName) throws TransformerException, IOException, SAXException {
        String newReservation = month + " " + day + " " + year + " " + hour + " " + minute + " "  + length + " " + topClientUsername;
        Node room = topClient.getStructHandler().lookup(currentBuilding.getName(), currentFloor, roomName);
        if (room == null) return false;
        String separator = StructureHandler.getReservationSep();
        System.out.println(room.getTextContent().trim().split(separator));
        return topClient.getStructHandler().checkValidity(newReservation, room.getTextContent().trim().split(separator), length);

    }
    public static void setCurrentRoom(Room currentRoom) {
        GraphicClientEndConnector.currentRoom = currentRoom;
    }


}
