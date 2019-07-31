package Backend;

import java.util.ArrayList;

public class ServerThreadManager {
    private ArrayList<ServerThread> _threads;
    private final int CONNECTION_LIMIT;


    public ServerThreadManager(int limit){
        this.CONNECTION_LIMIT = limit;
        this._threads = new ArrayList<ServerThread>();
    }
    public static void threadCheckStart(ServerThread t){
        if (!t.isAlive()) t.start();
    }

    public void addThread(ServerThread t){

        if (t != null)  {
            threadCheckStart(t);
            this._threads.add(t);
        }
        else {
            throw new NullPointerException("Expected non-empty thread");
        }
    }

    public int getConnectionCount(){
        return _threads.size();
    }
}
