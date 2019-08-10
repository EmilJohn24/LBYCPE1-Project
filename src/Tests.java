import Server.Structures.*;
import Server.Transaction.Account;
import Server.Transaction.UserDatabase;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;

public class Tests {
    @Test
    public void xmlParseTest(){

        UserDatabase testDatabase = new UserDatabase("users.txt");
        Account testAccount = Account.pseudoLogin("emil_lopez@dlsu.edu.ph", testDatabase);
        try {
            StructureHandler testParse = new StructureHandler("test.xml", testDatabase);
            System.out.println(testParse.addReservation("St. La Salle Hall", 1, "LS206", 2, 30, 1999, 10, 30,testAccount));
            ArrayList<Building> result = testParse.parse();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
