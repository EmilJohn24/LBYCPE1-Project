import Server.Structures.*;
import Server.Transaction.UserDatabase;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Tests {
    @Test
    public void xmlParseTest(){

        UserDatabase testDatabase = new UserDatabase("users.txt");
        try {
            StructureParser testParse = new StructureParser("test.xml", testDatabase);
            ArrayList<Building> result = testParse.parse();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
