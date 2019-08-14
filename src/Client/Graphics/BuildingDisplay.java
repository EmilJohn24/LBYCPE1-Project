package Client.Graphics;

import Server.Structures.Building;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;


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
        try {
            nav.loadFloor(3);
            nav.displayCurrentFloor();
            nav.runner();

        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
    public void run(){
        clickSetup();
    }

}
