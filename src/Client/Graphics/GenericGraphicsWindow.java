package Client.Graphics;

import Server.Structures.ResizableStruct;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;
import java.util.ArrayList;



public abstract class GenericGraphicsWindow<RS extends ResizableStruct>extends GraphicsProgram{
    private ArrayList<RS> structures;
    public void addToGraphics(GObject graphic){
        add(graphic);
    }

    public ArrayList<RS> getStructures(){
        return structures;
    }

    public RS getStructWithGRect(GRect rect){
        for (RS structure : structures){
            if (rect.equals(structure.getGraphic())){
                return structure;
            }
        }

        return null;
    }
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        GObject interactedElement = getElementAt(x, y);
        if (interactedElement instanceof GRect){
            GRect element = (GRect) interactedElement;
            highLevelClickHandler(getStructWithGRect(element));
        }
    }

    public abstract void highLevelClickHandler(RS clickedObject);

    public void clickSetup(){
        addMouseListeners();

    }
    private final double labelRatio = 0.7;
    public void loadUpGraphics(ArrayList<RS> structures){
        this.structures = structures;
        for (RS structure : structures){
            GRect tmp = structure.getGraphic();
            GLabel tmpLabel = new GLabel(structure.getName(), structure.getLeft() + structure.getWidth() * labelRatio, structure.getTop());
            this.addToGraphics(tmp);
            this.addToGraphics(tmpLabel);
        }
    }
}
