package Client;

import java.io.IOException;

public class Client {
    private ConnectorSocket _socket;

    public void log(String message){
        System.out.println(message);
    }

    public Client(String hostname, int port){
        try {
            _socket = new ConnectorSocket(hostname, port);
        }
        catch (IOException e){
            log("Error establishing connection. Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendRequest(String requestStr) throws Exception {
        if (_socket.isConnected())
            _socket.sendRequest(requestStr);
        else throw new Exception("Error connecting to server");
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
