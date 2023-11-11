package org.sample.Entity;

import org.dyn4j.geometry.Polygon;

import java.util.Arrays;

public class MixPolygon {
    public Polygon polygon;
    public Point center;

    public MixPolygon(){}

    public MixPolygon(Polygon polygon, Point center) {
        this.polygon = polygon;
        this.center = center;
    }

    @Override
    public String toString() {
        return "Polygon:"+ Arrays.toString(polygon.getVertices())+
                "\nCenter:("+center.x+","+center.y+")\n";
    }
}
