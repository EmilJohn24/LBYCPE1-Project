package Server.Transaction;

import Server.Structures.Building;
import Server.Structures.StructureHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;

//central manager of every operation
//will be set as a static class
public class Manager {

    private static UserDatabase database;
    private static StructureHandler parser;
    private static ArrayList<Building> reservations;
    public Account getAccountLogin(String username,
                                   String password){
        return Account.login(username, password, database);
    }

    static {
        database = new UserDatabase("users.txt");

        try {
            parser = new StructureHandler("test.xml", database);
            reservations = parser.parse();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String reserve(Account acc, String building, Integer floor, String room, int month, int day, int year, int hour, int minute) throws TransformerException, IOException, SAXException {
        String response =  parser.addReservation(building, floor, room, month, day, year, hour, minute, acc);
        return response;
    }
}
