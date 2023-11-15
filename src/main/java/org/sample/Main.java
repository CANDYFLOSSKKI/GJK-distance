package org.sample;

import org.sample.Entity.MixPolygon;
import org.sample.Entity.Position;
import org.sample.Process.Devide;
import org.sample.Process.Distance;
import org.sample.Process.PolygonBuilder;
import org.sample.Process.DataReader;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String strCase = "case3";
        ArrayList<Position> list = DataReader.read(strCase);
        ArrayList<MixPolygon> polygonList = new ArrayList<>();
        assert list!=null;
        for(Position pos:list) {
            polygonList.add(PolygonBuilder.getPolygon(pos));
        }
        long stime = System.currentTimeMillis();
        double dist = Devide.search(polygonList);
        long etime = System.currentTimeMillis();
        System.out.println("case: "+strCase);
        System.out.println("dist: "+dist);
        System.out.println("time: "+(etime-stime)+"ms");
    }
}
