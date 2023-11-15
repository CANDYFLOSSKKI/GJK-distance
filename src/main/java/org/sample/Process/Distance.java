package org.sample.Process;


import org.sample.Entity.MixPolygon;
import org.sample.Entity.Point;
import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.collision.narrowphase.Separation;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
public class Distance {
    //GJK算法实例对象(内部成员不作更改)
    public static Gjk gjkAlgo = new Gjk();
    //凸多边形参与计算前平移和旋转的数值,本算法无需进行该操作
    public static Transform defaultTF = new Transform();
    //计算两个凸四边形间的最短距离
    //输入:两个凸多边形对象(也可以是原生Polygon,不涉及形心)
    public static double betweenPolygon(MixPolygon p1, MixPolygon p2){
        //存储最短距离,以及最短距离对应凸多边形上的顶点向量坐标
        Separation sep = new Separation();
        //未发生碰撞GJK返回true同时为sep赋值,返回最短距离
        if(gjkAlgo.distance(p1.polygon,defaultTF,p2.polygon,defaultTF,sep)){
            return sep.getDistance();
        }
        //发生碰撞返回0
        return 0.0;
    }
    //向量坐标间距离计算
    public static double betweenVector(Vector2 vec1, Vector2 vec2){
        return Math.sqrt(Math.pow((vec1.x-vec2.x),2)+Math.pow((vec1.y-vec2.y),2));
    }
}
