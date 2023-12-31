package org.sample.Process;


import org.sample.Entity.MixPolygon;
import org.sample.Entity.Point;
import org.sample.Entity.Position;

import java.util.Arrays;
import java.util.Comparator;

import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;
public class PolygonBuilder {
    //凸四边形顶点坐标转凸四边形+形心对象
    public static MixPolygon getPolygon(Position position){
        Vector2[] vectors = toClockWiseVector(position);
        return new MixPolygon(
                toPolygon(vectors),
                getCenter(vectors)
        );
    }
    //顶点向量构造凸四边形
    public static Polygon toPolygon(Vector2[] vectors){
        return new Polygon(vectors);
    }
    //顶点坐标改顺时针顺序
    public static Vector2[] toClockWiseVector(Position position) {
        Vector2[] vectors = new Vector2[4];
        vectors[0] = new Vector2(position.one.x, position.one.y);
        vectors[1] = new Vector2(position.two.x, position.two.y);
        vectors[2] = new Vector2(position.three.x, position.three.y);
        vectors[3] = new Vector2(position.four.x, position.four.y);
        Point avg = new Point(position.getXSum()/4,position.getYSum()/4);
        Arrays.sort(vectors, Comparator.comparingDouble(o -> Math.atan2(o.y - avg.y, o.x - avg.x)));
        return vectors;
    }
    //计算形心坐标
    //输入:凸四边形顶点向量坐标
    public static Point getCenter(Vector2[] vectors){
        //1:求分割三角形的形心
        Point centerP1 = new Point(
                (vectors[0].x+vectors[1].x+vectors[2].x)/3,
                (vectors[0].y+vectors[1].y+vectors[2].y)/3
        );
        Point centerP2 = new Point(
                (vectors[2].x+vectors[3].x+vectors[0].x)/3,
                (vectors[2].y+vectors[3].y+vectors[0].y)/3
        );
        //2:求分割三角形的面积
        double areaP1 = getArea(vectors[0],vectors[1],vectors[2]);
        double areaP2 = getArea(vectors[2],vectors[3],vectors[0]);
        //3:应用公式求形心坐标
        return new Point(
                (centerP1.x*areaP1+centerP2.x*areaP2)/(areaP1+areaP2),
                (centerP1.y*areaP1+centerP2.y*areaP2)/(areaP1+areaP2)
        );
    }
    //海伦公式计算三角形面积
    public static double getArea(Vector2 vec1,Vector2 vec2,Vector2 vec3){
        //1:差平方法求两点距离
        double a = Distance.betweenVector(vec1, vec2);
        double b = Distance.betweenVector(vec2,vec3);
        double c = Distance.betweenVector(vec3,vec1);
        //2:应用海伦公式求面积
        double p = (a+b+c)/2;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }
}
