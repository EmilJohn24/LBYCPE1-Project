package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Runner {
    boolean open;

    Runner(){
        open = false;
    }

    public void openRunner(){
        open = true;
    }

    public void closeRunner(){
        open = false;
    }

    public ServerThread listen(int portNumber) throws IOException{
        try (ServerSocket socket = new ServerSocket(portNumber)){
            while (open){
                return new ServerThread(socket.accept());
            }
        }



        return null;
    }
}
