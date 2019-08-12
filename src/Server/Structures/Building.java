package Server.Structures;

import acm.graphics.GRect;

import java.util.ArrayList;

public class Building implements ResizableStruct{
    private String name;
    private ArrayList<Floor> floors;
    private double top;
    private double left;
    private double width;
    private double height;
    private GRect graphic;

    Building(String name, double top, double left, double width, double height){
        this.name = name;
        this.top = top; //Y-coord
        this.left = left; //X-coord
        this.height = height;
        this.width = width;
        this.floors = new ArrayList<>();
        graphic = new GRect(left, top, width, height);
    }

    Building(){
        this.name = "";
        this.top = 0;
        this.left = 0;
        this.height = 0;
        this.width = 0;
        this.floors = new ArrayList<>();
        graphic = new GRect(this.left, this.top, this.width, this.height);
    }

    public ArrayList<Floor> getFloors(){return this.floors;}

    public void addFloor(Floor newFloor){
        floors.add(newFloor);
    }

    public void createFloor(int floorCount, int height, int width){
        addFloor(new Floor(floorCount, width, height));
    }


    public GRect getGraphic() {
        return graphic;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        graphic.setSize(graphic.getWidth(), height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        graphic.setSize(width, graphic.getHeight());
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
        graphic.setX(left);
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
        graphic.setY(top);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

