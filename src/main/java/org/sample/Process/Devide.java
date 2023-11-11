package org.sample.Process;

import org.sample.Entity.MixPolygon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Devide {
    public static ArrayList<MixPolygon> list;
    public static double search(ArrayList<MixPolygon> polygonList) {
        polygonList.sort(Comparator.comparingDouble(i -> i.center.x));
        list = polygonList;
        return devide(0,polygonList.size()-1);
    }

    public static double devide(Integer start, Integer end){
        double dist = -1.0;
        if(end-start<=3) {
            for(int i=start;i<=end;++i){
                for(int j=i+1;j<=end;++j){
                    dist = updateDist(dist,i,j);
                }
            }
        } else {
            int middle = (start+end)>>1;
            dist = merge(start,middle,end, Math.min(devide(start,middle), devide(middle+1,end)));
        }
        return Math.max(dist,0.0);
    }

    public static double updateDist(Double origin,Integer i,Integer j){
        double after = Distance.betweenPolygon(list.get(i),list.get(j));
        return (origin<0|origin>after)?after:origin;
    }
    public static double updateDistInMerge(Double origin,Double after){
        return origin>after?after:origin;
    }

    public static double merge(Integer start,Integer middle,Integer end,double minDist){
        double dist = minDist;
        double midX = list.get(middle).center.x;
        ArrayList<MixPolygon> listInH = new ArrayList<>(list.subList(start,end+1).stream()
                .filter(i->(i.center.x>=midX-minDist)&&(i.center.x<=midX+minDist))
                .sorted(Comparator.comparingDouble(i -> 0-i.center.y))
                .toList());
        for(int i=0;i<listInH.size();++i) {
            for(int j=i+1;j<listInH.size();++j){
                dist = updateDistInMerge(dist,Distance.betweenPolygon(listInH.get(i),listInH.get(j)));
            }
        }
        return Math.max(dist,0.0);
    }
}
