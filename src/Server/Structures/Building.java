package Server.Structures;

import java.util.ArrayList;

public class Building {
    private String name;
    private ArrayList<Floor> floors;
    private String directory;


    Building(String name, String fileDirectory){
        directory = fileDirectory;
        this.name = name;
    }
    //TODO: Floor Loader
    public void loadFloors(){
        return;
    }
}

