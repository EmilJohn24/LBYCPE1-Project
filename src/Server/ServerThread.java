package Server;

import Server.Transaction.Account;
import Server.Transaction.SessionManager;
import Server.Transaction.Manager;
import org.xml.sax.SAXException;

import javax.security.auth.login.FailedLoginException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class ServerThread extends Thread{
    private Socket _socket;
    private PrintWriter output;
    private BufferedReader input;
    private Integer sessionID;
    public ServerThread(Socket socket) throws IOException{
        super("Thread::" + socket.getInetAddress());
        this._socket = socket;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(
                                    socket.getInputStream()));
        ServerLog.globalLog("New thread opened for " + socket.getInetAddress());
    }


    //handles requests from the client. For now, it simply displays them as messages.
    //Request Format:
    //[REQUEST_TYPE]:PARAMS
    //Normal Types:
    //LOGIN:[username],[password]
    //GET_ROOM_DATA
    //RESERVE:[SESSIONID],[BUILDING NAME],[FLOOR],[ROOM],[MONTH],[DAY],[YEAR],[HOUR],[MINUTE]


    //Returnable Messages:
    //SENDING_ROOM_DATA [ROOM_DATA] SENT_ROOM_DATA
    //ERROR_SENDING_FILE
    //XML_FILE_NOT_FOUND
    //ERROR_RESERVE
    //LOGIN_SUCCESS:[SESSIONID]
    //LOGIN_FAILED
    //INVALID_CREDENTIALS
    //RANDOM_NETWORK_ERROR
    //ROOM_NOT_AVAILABLE
    private String process(String request){

        ServerLog.globalLog("Message: " + request);
        String[] requestComponents = request.split(":");
        String requestID = requestComponents[0];
        System.out.println(requestID);
        String params[] = new String[10];
        if (requestComponents.length > 1)  params = requestComponents[1].split(",");
        switch (requestID){
            case "LOGIN":
                return loginRequestHandler(params[0], params[1]);
            case "GET_ROOM_DATA":
                return roomRequestHandler(); //send xml via manager request. Return ID indicating the transfer is complete
            case "RESERVE":
                return reservationHandler(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]); //
        }

        return null;
    }

    private String roomRequestHandler(){
        File structureFile = SessionManager.getStructureFile();
        String dataLine;
        send("SENDING_ROOM_DATA");
        try {
            BufferedReader structureFileReader = new BufferedReader(new FileReader(structureFile));
            while ((dataLine = structureFileReader.readLine()) != null) {
                send(dataLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "XML_FILE_NOT_FOUND";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR_SENDING_FILE";
        } finally{
            return "SENT_ROOM_DATA";
        }


    }

    private String reservationHandler(String sessionID, String building, String floor, String room, String month, String day, String year, String hour, String minute){
        try {
            return SessionManager.reserve(sessionID, building, floor, room, Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year), Integer.parseInt(hour), Integer.parseInt(minute));
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }
    private String loginRequestHandler(String username, String password){
        try {
            sessionID = SessionManager.addSession(username, password);
            if (sessionID != 0){
                return "LOGIN_SUCCESS" + ":" + String.valueOf(sessionID);
            }
            else{
                return "INVALID_CREDENTIALS";
            }
        } catch (FailedLoginException e) {
            return "LOGIN_FAILED";
        }
    }




    public void start(){
        String request;
        String message;
        try {
            while (true) {
                request = listen();
                message = process(request);
                send(message);
            }
        }
        catch(IOException e){
            ServerLog.globalLog("Socket " + _socket.getInetAddress() + " has crashed.");
            e.printStackTrace();
        }
    }


    //private final int LISTEN_TIMEOUT = 1000;
    //private int LISTEN_COUNT = 0;

    private String listen() throws IOException{
        String request;
        while (true){
            //if (LISTEN_COUNT == LISTEN_TIMEOUT) break;
            request = input.readLine();
            if (request != null) return request;
        }
    }

    public void close() throws IOException, InterruptedException {
        _socket.close();
        this.join();

    }

    public void send(String message){
        output.println(message);

    }

    public InetAddress getID(){
        return _socket.getInetAddress();
    }

}
