package Client.Graphics;

import Server.Structures.Building;
import Server.Structures.ResizableStruct;


public class BuildingDisplay extends GenericGraphicsWindow<Building> {
    private Building lastExploredBuilding;
    private BuildingNavigator nav;
    public BuildingDisplay(){
        super();
    }

    public Building getLastExploredBuilding(){
        return lastExploredBuilding;
    }

    public BuildingNavigator getNav(){
        return nav;
    }

    public void highLevelClickHandler(Building clickedObject){
        System.out.println("Loading room:" + clickedObject.getName());
        nav = new BuildingNavigator(clickedObject);
        //TEST
        nav.loadFloor(3);
        nav.displayCurrentFloor();

    }
    public void run(){
        clickSetup();
    }

}
