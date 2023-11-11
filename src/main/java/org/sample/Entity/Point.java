package org.sample.Entity;

public class Point {
    public Double x;
    public Double y;

    public Point(){}

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAll(double x,double y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "Point:"+"("+x+","+y+")";
    }
}
