package Server.Structures;

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
                    <slot time="12:30">emil_lopez@dlsu.edu.ph</slot>
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
    private Document structureDoc;
    private final String inspector = "dlsu";
    private final String buildingsIndicator = "building";
    private final String floorIndicator = "floor";
    private final String roomIndicator = "room";
    private final String descriptionIndicator = "description";
    private final String dateIndicator = "date";
    private final String slotIndicator = "slot";
    StructureParser(String fileName) throws ParserConfigurationException,
                                    IOException, SAXException {
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

    private void chuckFloors(Building currentBuilding){

    }

    public static double getElementAsDouble(Element e, String attr){
        return Double.valueOf(e.getAttribute(attr));
    }

    public ArrayList<Building> parse(){

        ArrayList<Building> buildingList = new ArrayList<>();
        //top-level breakdown

        NodeList buildingNodes = structureDoc.getElementsByTagName(buildingsIndicator);
        for (int bCount = 0; bCount < buildingNodes.getLength(); bCount++){
            Building newBuilding = new Building();
            Element currentBuilding = (Element) buildingNodes.item(bCount);
            newBuilding.setName(currentBuilding.getAttribute("name"));
            newBuilding.setTop(getElementAsDouble(currentBuilding,"top"));
            newBuilding.setLeft(getElementAsDouble(currentBuilding, "left"));
            newBuilding.setHeight(getElementAsDouble(currentBuilding, "height"));
            newBuilding.setWidth(getElementAsDouble(currentBuilding, "width"));
            


        }
        return buildingList;
    }
}
