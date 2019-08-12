package Client;

import java.io.IOException;

public class Client {
    private ConnectorSocket _socket;

    public void log(String message){
        System.out.println(message);
    }

    public Client(String hostname, int port) throws IOException {
        _socket = new ConnectorSocket(hostname, port);

    }

    public void sendRequest(String requestStr) {
        _socket.sendRequest(requestStr);

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
