package Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket _socket;
    PrintWriter output;
    BufferedReader input;
    public ServerThread(Socket socket) throws IOException{
        super("Thread::" + socket.getInetAddress());
        this._socket = socket;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(
                                    socket.getInputStream()));
        ServerLog.globalLog("New thread opened for " + socket.getInetAddress());
    }



    private void process(String request){
        ServerLog.globalLog("Message: " + request);
    }


    public void start(){
        String request;
        try {
            while (true) {
                request = listen();
                process(request);
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

    public InetAddress getID(){
        return _socket.getInetAddress();
    }

}
