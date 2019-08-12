package Client;

import Client.Graphics.BuildingDisplay;
import Server.Structures.Building;
import Server.Structures.StructureHandler;
import Server.Transaction.Account;
import Server.Transaction.UserDatabase;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class Client {
    private ConnectorSocket _socket;
    private StructureHandler handler;
    private UserDatabase localUserCopy;
    private ArrayList<Building> buildings;
    private int sessionID; //TODO: Implement proper login system
    //GUI area
    public void log(String message){
        System.out.println(message);
    }

    public Client(String hostname, int port) throws IOException {
        _socket = new ConnectorSocket(hostname, port);
        localUserCopy = new UserDatabase("users.txt"); //TODO: Lopez: delocalize users.txt access in future update
                                                                // I recommend creating a function for loading this that requests password-less database transfer

    }

    public void sendRequest(String requestStr) {
        _socket.sendRequest(requestStr);
    }

    public void loadBuildings() throws SAXException, ParserConfigurationException, TransformerConfigurationException, IOException {
        if (handler == null) loadRoomData();
        buildings = handler.parse();
    }

    public void loadBuildingGUI() throws SAXException, ParserConfigurationException, TransformerConfigurationException, IOException {
        if (buildings == null) loadBuildings();

        GraphicClientEndConnector.connectClient(this);
    }

    public void loadRoomData() throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        sendRequest("GET_ROOM_DATA");
        String response = getResponse();
        String[] responseComponents = response.split(":");
        String id = responseComponents[0];
        String data;
        if (id.equals("ROOM_DATA")){
            data = responseComponents[1];
            BufferedWriter tempWriter = new BufferedWriter(new FileWriter("temp.xml"));
            tempWriter.write(data);
            tempWriter.close();
            handler = new StructureHandler("temp.xml", localUserCopy);

        }

        else if (id.equals("ERROR_SENDING_FILE")){
            log("Well fuck");
            System.exit(0);
        }
        else if (id == null){
            log("Null string bois");
        }



    }

    public String getResponse(){
        try {
            return _socket.getResponse();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
