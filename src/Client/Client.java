package Client;

import java.io.IOException;

public class Client {
    private ConnectorSocket _socket;

    public void log(String message){
        System.out.println(message);
    }
    Client(String hostname, int port){
        try {
            _socket = new ConnectorSocket(hostname, port);
        }
        catch (IOException e){
            log("Error establishing connection. Error: " + e.getMessage());
        }
    }

    public void sendRequest(String requestStr){
        _socket.sendRequest(requestStr);
    }

    public String getResponse(){
        try {
            return _socket.getResponse();
        }
        catch(IOException e){
            log("Error communicating with server.");
        }

        return null;
    }
}
