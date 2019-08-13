package Server.Structures;

import Server.Transaction.Account;
import Server.Transaction.UserDatabase;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.imageio.metadata.IIOMetadataNode;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/*
<dlsu>
    <building name="Razon" top="10" left="10" height="10" width="10">
        <floor number="1" height="100" width="100">
            <room top="10" left="10" width="30" height="30" name="Room">
                [month] [day] [year] [hour] [minute] [email] [reason]::::
            </room>
        </floor>
    </building>
</dlsu>

 */
//This uses the Java DOM XML Parser which requires complete knowledge of
//all tags used in the file
public class StructureHandler {
    private File structureFile;
    private DocumentBuilder build;
    private UserDatabase tempDatabase;
    private Document structureDoc;

    private Transformer updater;
    private StreamResult result;
    private DOMSource src;


    private final String inspector = "dlsu";
    private final String buildingsIndicator = "building";
    private final String floorIndicator = "floor";
    private final String roomIndicator = "room";
    private final String descriptionIndicator = "description";
    private final String dateIndicator = "date";
    private final String slotIndicator = "slot";

    public File getStructureFile(){
        return structureFile;
    }

    public StructureHandler(String fileName, UserDatabase users) throws ParserConfigurationException,
            IOException, SAXException, TransformerConfigurationException {
        this.tempDatabase = users;
        structureFile = new File(fileName);
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        build = factory.newDocumentBuilder();
        structureDoc = build.parse(structureFile);
        structureDoc.getDocumentElement().normalize();
        if (!structureDoc.getDocumentElement().getNodeName().equals(inspector)){
            throw new InvalidPropertiesFormatException("Not a valid DLSU " +
                                                    "Structure XML file");
        }

        TransformerFactory factoryOut = TransformerFactory.newInstance();
        updater = factoryOut.newTransformer();
        src = new DOMSource(structureDoc);
        result = new StreamResult(structureFile);

    }

    //Looks up exact node to reservation to
/*    //No checks will be performed here, do it lower in the stack
    public String addReservation(String building, Integer floor, String room, int month, int day, int year, int hour, int minute, Account user){

        NodeList top = structureDoc.getElementsByTagName(buildingsIndicator);
        for (int i = 0; i < top.getLength(); i++){
            Element currentBuilding = (Element) top.item(i);
            if (currentBuilding.getAttribute("name").equals(building)) {
                NodeList floorLayer = currentBuilding.getChildNodes();
                for (int j = 0; j < floorLayer.getLength(); j++) {
                    Element currentFloor = (Element) floorLayer.item(j);
                    if (getElementAsInt(currentFloor, "number") == floor){
                        NodeList roomLayer = currentFloor.getChildNodes();
                        for (int k = 0; k < roomLayer.getLength(); k++){
                            Element currentRoom = (Element) roomLayer.item(k);
                            if (currentRoom.getAttribute("name").equals(room)){
                            }
                        }
                    }
                }
            }
        }

        return null;
    }*/
    private final static String reservationSep = "::::";

    public Element lookup(String building, Integer floor, String room) throws TransformerException, IOException, SAXException{
        NodeList buildingNodes = structureDoc.getElementsByTagName(buildingsIndicator);
        for (int bCount = 0; bCount < buildingNodes.getLength(); bCount++){

            Element currentBuilding = (Element) buildingNodes.item(bCount);
            if (building.equals(currentBuilding.getAttribute("name"))){
                NodeList floorNodes = currentBuilding.getElementsByTagName(floorIndicator);
                for (int fCount = 0; fCount < floorNodes.getLength(); fCount++){

                    Element currentFloor = (Element) floorNodes.item(fCount);
                    if (floor == getElementAsInt(currentFloor, "number")){
                        NodeList roomNodes = currentFloor.getElementsByTagName(roomIndicator);

                        for (int rCount = 0; rCount < roomNodes.getLength(); rCount++){
                            Element currentRoom = (Element) roomNodes.item(rCount);
                            if (room.equals(currentRoom.getAttribute("name"))){
                                return currentRoom;


                            }
                        }
                        break;
                    }

                }
                break;
            }
        }
        return null;
    }
                //[month] [day] [year] [hour] [minute] [email] [reason]::::

    public Calendar createComparatorDate(String formatText){
        String[] dataStr = formatText.split(" ");
        Integer[] data = new Integer[6];
        for (int i = 0; i < dataStr.length; i++){
            data[i] = Integer.parseInt(dataStr[i]);
        }
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(data[2], data[0], data[1], data[3], data[4], data[5]);
        return newCalendar;

    }

    public boolean checkValidity(String reservation, String[] otherReservations, int maxLength){
        Calendar reservationDate = createComparatorDate(reservation);
        ArrayList<Calendar> comparisonCalendars = new ArrayList<>();
        int difference;
        double secondDifference;
        for (Calendar c : comparisonCalendars){
            difference = reservationDate.compareTo(c);
            secondDifference = difference / 1000;
            if (secondDifference < maxLength) return false;
        }

        return true;



    }


    public static void appendToReservation(Element e, String text){
        e.setTextContent(e.getTextContent() + reservationSep + text);
    }

    public String addReservationTo(String building, Integer floor, String room, int month, int day, int year, int hour, int minute, int length, Account user) throws TransformerException, IOException, SAXException {
        String newReservation = month + " " + day + " " + year + " " + hour + " " + minute + " " + user.getUsername();
        Element matchingRoom = lookup(building, floor, room);
        String[] otherReservations = matchingRoom.getTextContent().split(reservationSep);
        if (checkValidity(newReservation, otherReservations, length)){
            appendToReservation(matchingRoom, newReservation);
            return "RESERVATION_COMPLETE";
        }
        else return "ROOM_NOT_AVAILABLE";
    }

    public void updateFile() throws TransformerException, IOException, SAXException {
        updater.transform(src, result);
        structureDoc = build.parse(structureFile);

    }


    public static double getElementAsDouble(Element e, String attr){
        return Double.parseDouble(e.getAttribute(attr));
    }

    public static int getElementAsInt(Element e, String attr){
        return Integer.parseInt(e.getAttribute(attr));
    }

    public void resizeStructWithXMLAttribs(ResizableStruct rs, Element xmlElem){
        rs.setTop(getElementAsDouble(xmlElem,"top"));
        rs.setLeft(getElementAsDouble(xmlElem, "left"));
        rs.setHeight(getElementAsDouble(xmlElem, "height"));
        rs.setWidth(getElementAsDouble(xmlElem, "width"));
    }

    public ArrayList<Building> parse(){
        ArrayList<Building> buildingList = new ArrayList<>();
        //top-level breakdown

        NodeList buildingNodes = structureDoc.getElementsByTagName(buildingsIndicator);
        for (int bCount = 0; bCount < buildingNodes.getLength(); bCount++){
            Building newBuilding = new Building();
            Element currentBuilding = (Element) buildingNodes.item(bCount);
            newBuilding.setName(currentBuilding.getAttribute("name"));
            resizeStructWithXMLAttribs(newBuilding, currentBuilding);

            chuckFloors(newBuilding,
                    currentBuilding.getElementsByTagName(floorIndicator));

            buildingList.add(newBuilding);
        }

        return buildingList;
    }

    private void chuckFloors(Building newBuilding, NodeList floors){
        for (int fCount = 0; fCount < floors.getLength(); fCount++){
            Element currentFloor = (Element) floors.item(fCount);

            int floorCount = getElementAsInt(currentFloor, "number");
            int width = getElementAsInt(currentFloor, "width");
            int height = getElementAsInt(currentFloor, "height");

            Floor newFloor = new Floor(floorCount, width, height);
            chuckRooms(newFloor,
                    currentFloor.getElementsByTagName(roomIndicator));
            newBuilding.addFloor(newFloor);

        }
    }

    private void chuckRooms(Floor newFloor, NodeList rooms){
        for (int rCount = 0; rCount < rooms.getLength(); rCount++){
            Element currentRoom = (Element) rooms.item(rCount);
            Room newRoom = new Room();
            newRoom.setName(currentRoom.getAttribute("name"));
            resizeStructWithXMLAttribs(newRoom, currentRoom);
            Element descriptor = (Element) currentRoom.
                        getElementsByTagName(descriptionIndicator).item(0);
            newRoom.setDescription(descriptor.getNodeValue());
            chuckSlots(newRoom, currentRoom.getElementsByTagName(dateIndicator));
            newFloor.addRoom(newRoom);


        }
    }

    private void chuckSlots(Room newRoom, NodeList dates){
        for (int dCount = 0; dCount < dates.getLength(); dCount++){
            Element currentDate = (Element) dates.item(dCount);
            int day = getElementAsInt(currentDate, "day");
            int month = getElementAsInt(currentDate, "month");
            int year = getElementAsInt(currentDate, "year");
            NodeList daySlots = currentDate.getElementsByTagName(slotIndicator);

            for (int sCount = 0; sCount < daySlots.getLength(); sCount++){
                Element currentSlot = (Element) daySlots.item(sCount);
                int hour = getElementAsInt(currentSlot, "hour");
                int minute = getElementAsInt(currentSlot, "minute");
                String username = currentDate.getTextContent().trim();
                Account transientLogin = Account.pseudoLogin(username, tempDatabase);

                if (transientLogin != null) newRoom.fillSlot(transientLogin, month, day, year, hour, minute);
                else newRoom.addEmptySlot(month, day, year, hour, minute);
            }
        }
    }
}
