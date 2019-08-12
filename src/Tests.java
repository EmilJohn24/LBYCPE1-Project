import Client.Client;
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

    Client dummyClient;

    public Tests(){
        dummyClient = new Client("127.0.0.1", 4400);
    }
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
        try {
            dummyClient.sendRequest("RESERVE:" + sessionID + ",Velasco Hall,3,V307,2,29,1999,11,30");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(dummyClient.getResponse());
        //RESERVE:[SESSIONID],[BUILDING NAME],[FLOOR],[ROOM],[MONTH],[DAY],[YEAR],[HOUR],[MINUTE]
    }
     

    @Test
    public void roomRequestTest(){
        try {
            dummyClient.sendRequest("GET_ROOM_DATA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = dummyClient.getResponse();
        if (response.equals("SENDING_ROOM_DATA")){
            System.out.println(dummyClient.getResponse());
        }

    }
}
