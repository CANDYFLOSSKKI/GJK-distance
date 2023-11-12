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
        ArrayList<Position> list = DataReader.read("case1");
        ArrayList<MixPolygon> polygonList = new ArrayList<>();
        assert list!=null;
        for(Position pos:list) {
            polygonList.add(PolygonBuilder.getPolygon(pos));
        }
        double dist = Devide.search(polygonList);
        System.out.println(dist);
    }
}
