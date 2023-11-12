package org.sample.Process;

import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.collision.narrowphase.Separation;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.sample.Entity.MixPolygon;
import org.sample.Entity.Point;

public class Distance {
    public static Gjk gjkAlgo = new Gjk();
    public static Transform defaultTF = new Transform();
    public static double betweenPolygon(MixPolygon p1, MixPolygon p2){
        Separation sep = new Separation();
        if(gjkAlgo.distance(p1.polygon,defaultTF,p2.polygon,defaultTF,sep)){
            return sep.getDistance();
        }
        return 0.0;
    }
    public static double betweenVector(Vector2 vec1, Vector2 vec2){
        return Math.sqrt(Math.pow((vec1.x-vec2.x),2)+Math.pow((vec1.y-vec2.y),2));
    }
    public static double betweenPoint(Point p1, Point p2){
        return Math.sqrt(Math.pow((p1.x-p2.x),2)+Math.pow((p1.y-p2.y),2));
    }
}
