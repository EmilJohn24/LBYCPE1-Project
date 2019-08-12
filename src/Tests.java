import Client.Client;
import Client.Graphics.BuildingDisplay;
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

    private Client dummyClient;

    public Tests(){
        try {
            dummyClient = new Client("127.0.0.1", 4400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void xmlParseTest(){

        UserDatabase testDatabase = new UserDatabase("users.txt");
        Account testAccount = Account.pseudoLogin("emil_lopez@dlsu.edu.ph", testDatabase);
        try {
            StructureHandler testParse = new StructureHandler("test.xml", testDatabase);
            System.out.println(testParse.addReservation("St. La Salle Hall", 1, "LS206", 2, 30, 1999, 10, 30,testAccount));
            ArrayList<Building> result = testParse.parse();
            BuildingDisplay buildingDisplay = new BuildingDisplay();
            buildingDisplay.loadUpGraphics(result);
            buildingDisplay.start();
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

    @Test
    public void connectionAndReservationTest(){
        try {
            dummyClient.sendRequest("LOGIN:emil_lopez@dlsu.edu.ph,fakepassword");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sessionResponse = dummyClient.getResponse();
        System.out.println(sessionResponse);
        String sessionID = sessionResponse.split(":")[1];
        dummyClient.sendRequest("RESERVE:" + sessionID + ",Velasco Hall,3,V307,2,29,1999,11,30");
        System.out.println(dummyClient.getResponse());
        //RESERVE:[SESSIONID],[BUILDING NAME],[FLOOR],[ROOM],[MONTH],[DAY],[YEAR],[HOUR],[MINUTE]
    }
     

    @Test
    public void roomRequestTest() throws SAXException, ParserConfigurationException, TransformerConfigurationException, IOException {
        dummyClient.loadRoomData();
    }

    @Test
    public void loadBuildingTest(){
        try {
            dummyClient.loadBuildingGUI();
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
}
