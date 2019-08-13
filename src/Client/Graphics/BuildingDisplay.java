package Client.Graphics;

import Server.Structures.Building;


public class BuildingDisplay extends GenericGraphicsWindow<Building> {
    private BuildingNavigator nav;
    public BuildingDisplay(){
        super();
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
        nav.runner();

    }
    public void run(){
        clickSetup();
    }

}
