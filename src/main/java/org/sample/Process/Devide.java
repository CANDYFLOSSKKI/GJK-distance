package org.sample.Process;

import org.sample.Entity.MixPolygon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Devide {
    public static ArrayList<MixPolygon> list;
    //归并操作入口
    public static double search(ArrayList<MixPolygon> polygonList) {
        polygonList.sort(Comparator.comparingDouble(i -> i.center.x));
        list = polygonList;
        return devide(0,polygonList.size()-1);
    }
    //分割子区间
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
    //分割子区间到极小值时遍历更新距离
    public static double updateDist(Double origin,Integer i,Integer j){
        double after = Distance.betweenPolygon(list.get(i),list.get(j));
        return (origin<0|origin>after)?after:origin;
    }
    //合并子区间时遍历更新距离
    public static double updateDistInMerge(Double origin,Double after){
        return origin>after?after:origin;
    }
    //子区域合并
    //输入:左子区域起始下标,分隔点下标,右子区域起始下标,最小最短距离H
    public static double merge(Integer start,Integer middle,Integer end,double minDist){
        //1:出现碰撞时快速收敛
        if(minDist==0.0) { return 0.0; }
        double dist = minDist;
        double midX = list.get(middle).center.x;
        //2:建立中心区域
        ArrayList<MixPolygon> listInH = new ArrayList<>(list.subList(start,end+1).stream()
                //①过滤形心横坐标在[midX-H,midX+H]内的凸四边形
                .filter(i->(i.center.x>=midX-minDist)&&(i.center.x<=midX+minDist))
                //②按形心纵坐标排序
                .sorted(Comparator.comparingDouble(i -> 0-i.center.y)).toList());
        for(int i=0;i<listInH.size()-1;++i) {
            MixPolygon mp = listInH.get(i);
            //3:中心区域形心点向下划两个H×H的正方形,筛选其中形心点再更新距离
            for(MixPolygon np : listInH.subList(i+1,listInH.size()).stream().filter(p->(
                            (p.center.x>=mp.center.x-minDist)&
                            (p.center.x<=mp.center.x+minDist)&
                            (p.center.y>=mp.center.y-minDist))).toList()) {
                dist = updateDistInMerge(dist,Distance.betweenPolygon(mp,np));
            }
        }
        return Math.max(dist,0.0);
    }
}
