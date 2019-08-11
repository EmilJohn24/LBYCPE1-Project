package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//communicates with the server. For now, it simply displays them as messages.
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
public class ConnectorSocket extends Socket {
    private String _hostname;
    private int _port;
    private PrintWriter output;
    private BufferedReader input;

    public ConnectorSocket(String hostname, int port) throws IOException {
        super(hostname, port);

        this._hostname = hostname;
        this._port = port;
        this.output = new PrintWriter(getOutputStream(), true);
        this.input = new BufferedReader(
                            new InputStreamReader(getInputStream()));
    }

    public String getResponse() throws IOException{
        String response;
        while (true){
            response = input.readLine();
            if (response != null) return response;

        }
    }

    public String process(String request){
        return null;
    }



    public void sendRequest(String requestStr){
        output.println(requestStr);
    }
}
