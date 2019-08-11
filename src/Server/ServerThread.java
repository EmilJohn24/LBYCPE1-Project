package Server;

import Server.Transaction.SessionManager;

import javax.security.auth.login.FailedLoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

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
    //RESERVE:[SESSIONID],[BUILDING NAME],[FLOOR],[ROOM]


    //Returnable Messages:
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
        String[] params = requestComponents[1].split(",");
        switch (requestID){
            case "LOGIN":
                return loginRequestHandler(params[0], params[1]);
            case "GET_ROOM_DATA":
                return roomRequestHandler(); //return xml via manager request
            case "RESERVE":
                return reservationHandler(params[0], params[1], params[2], params[3], params[4]); //

        }

        return null;
    }

    private String roomRequestHandler(){
        return null;
    }

    private String reservationHandler(String sessionID, String building, String floor, String room, String time){
        return null;
    }
    private String loginRequestHandler(String username, String password){
        try {
            sessionID = SessionManager.addSession(username, password);
            if (sessionID != 0){
                return "LOGIN_SUCCESS" + " " + String.valueOf(sessionID);
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
