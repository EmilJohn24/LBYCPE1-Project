package Server.Structures;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
/*
<building name="Razon">
    <floor number="1" height="100" width="100">
        <room top="10" left="10" width="30" height="30" name="Room">
            <description>Very nice room</description>

            <date day="30" month="12" year="1999">
                <slot time="12:30">emil_lopez@dlsu.edu.ph</slot>
            </date>
        </room>
    </floor>
</building>


 */

public class StructureParser {
    private File structureFile;
    private DocumentBuilder build;
    private Document structureDoc;
    StructureParser(String fileName) throws ParserConfigurationException,
                                    IOException, SAXException {
        structureFile = new File(fileName);
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        build = factory.newDocumentBuilder();
        structureDoc = build.parse(structureFile);
    }

    public ArrayList<Building> parse(){
        ArrayList<Building> buildings = new ArrayList<>();

        return buildings;
    }
}
