package org.sample.Entity;

import javax.swing.*;

public class Position {
    public Point one;
    public Point two;
    public Point three;
    public Point four;
    public Position(double[] array){
        this.one=new Point(array[0],array[1]);
        this.two=new Point(array[2],array[3]);
        this.three=new Point(array[4],array[5]);
        this.four=new Point(array[6],array[7]);
    }
    public Position(){}
    public double getXSum(){
        return one.x+two.x+three.x+four.x;
    }
    public double getYSum(){
        return one.y+two.y+three.y+four.y;
    }
    @Override
    public String toString() {
        return "Position:["+
                "("+one.x+","+one.y+") "+
                "("+two.x+","+two.y+") "+
                "("+three.x+","+three.y+") "+
                "("+four.x+","+four.y+")]";
    }
}
