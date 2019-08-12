package Server.Structures;

import acm.graphics.GRect;

public interface ResizableStruct {
    void setWidth(double width);
    void setHeight(double height);
    void setTop(double top);
    void setLeft(double left);
    GRect getGraphic(); //all structures with a concept of resizability have a graphic object attached to them
    String getName();
    double getWidth();
    double getHeight();
    double getTop();
    double getLeft();
}
