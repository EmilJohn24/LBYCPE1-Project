package Server.Structures;

import Server.Transaction.Account;
import Server.Transaction.UserDatabase;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

/*
<dlsu>
    <building name="Razon" top="10" left="10" height="10" width="10">
        <floor number="1" height="100" width="100">
            <room top="10" left="10" width="30" height="30" name="Room">
                <description>Very nice room</description>

                <date day="30" month="12" year="1999">
                    <slot hour="12" minute="30" >emil_lopez@dlsu.edu.ph</slot>
                </date>
            </room>
        </floor>
    </building>
</dlsu>

 */
//This uses the Java DOM XML Parser which requires complete knowledge of
//all tags used in the file
public class StructureParser {
    private File structureFile;
    private DocumentBuilder build;
    private UserDatabase tempDatabase;
    private Document structureDoc;
    private final String inspector = "dlsu";
    private final String buildingsIndicator = "building";
    private final String floorIndicator = "floor";
    private final String roomIndicator = "room";
    private final String descriptionIndicator = "description";
    private final String dateIndicator = "date";
    private final String slotIndicator = "slot";
    StructureParser(String fileName, UserDatabase users) throws ParserConfigurationException,
                                    IOException, SAXException {
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
                String username = currentSlot.getNodeValue();
                Account transientLogin = Account.pseudoLogin(username, tempDatabase);
                if (!username.isEmpty()) newRoom.fillSlot(transientLogin, month, day, year, hour, minute);
                else newRoom.addEmptySlot(month, day, year, hour, minute);
            }
        }
    }
}
