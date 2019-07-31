package Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Backend {
    private ServerThreadManager _threadManager;
    private Runner _runner;
    private int _portNumber;

    public void log(String newLog){
        ServerLog.globalLog(newLog);
    }

    public Backend(int portNumber, int limit){
        this._portNumber = portNumber;
        _threadManager = new ServerThreadManager(limit);
        _runner = new Runner();
        _runner.openRunner();
    }

    public void takeConnections(){
        log("Started accepting connections...");
        while (true){
            try {
               ServerThread newSock = _runner.listen(_portNumber);
               _threadManager.addThread(newSock);
               log("Established connection with: " + newSock.getID());
            }
            catch (IOException e){
                log("Unable to listen to port " + _portNumber);
            }
            catch (NullPointerException e){
                log("No new connection established. Trying again...");
            }


        }
    }

}
