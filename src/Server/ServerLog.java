package Server;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ServerLog {
    //static members
    private static ServerLog global;

    static{
        global = new ServerLog();
    }

    public static void changeGlobalLogWriter(PrintWriter writer){
        global.changePrintWriter(writer, false);
    }

    public static void changeGlobalLog(ServerLog log){
        global = log;
    }

    public static void globalLog(String log){
        global.internalLog(log);
        //System.out.println(log);
    }
    //end of static members

    private PrintWriter out;
    private String name;
    private String context;

    public ServerLog(){
        this.out = new PrintWriter(System.out, true);
    }

    public ServerLog(String filename) throws FileNotFoundException {
        this.out = new PrintWriter(filename);
    }

    public ServerLog(OutputStream o){
        this.out = new PrintWriter(o);
    }

    public ServerLog(OutputStream o, boolean autoFlush){
        this.out = new PrintWriter(o, autoFlush);
    }


    public void internalLog(String message){
        out.println(message);
    }

    public void changePrintWriter(PrintWriter writer, boolean transfer){
        if (transfer) writer.print(out);
        out = writer;
    }


}
