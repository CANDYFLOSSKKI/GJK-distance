package org.sample.Process;

import org.sample.Entity.MixPolygon;

import java.util.*;
import java.util.function.UnaryOperator;

public class Devide {
    public static ArrayList<MixPolygon> list;
    //操作入口
    public static double search(ArrayList<MixPolygon> polygonList) {
        polygonList.sort(Comparator.comparingDouble(i -> i.center.x));
        list = polygonList;
        return devide(0,polygonList.size()-1);
    }
    //分割子区间
    public static double devide(Integer start, Integer end){
        double dist = -1.0;
        if(end-start<=3) {
            for(int i=start;i<=end;++i) {
                for (int j = i + 1; j <= end; ++j) {
                    dist = updateDist(dist, i, j);
                }
            }
            //对最小子区间按y进行排序
            list.subList(start,end+1).sort(Comparator.comparingDouble(i->i.center.y));
        } else {
            int middle = (start+end)>>1;
            //排序后下标为middle的横坐标会发生变化,递归时就预先存储
            double midX = list.get(middle).center.x;
            dist = merge(start,middle,end,midX,
                    Math.min(devide(start,middle),devide(middle+1,end)));
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
    public static double merge(Integer start,Integer middle,Integer end,Double midX,double minDist){
        //1:出现碰撞时快速收敛
        if(minDist==0.0) { return 0.0; }
        double dist = minDist;
        //2:对子区间执行归并排序的合并操作,得到y的有序序列
        ArrayList<MixPolygon> listSorted = new ArrayList<>();
        int a1=start;
        int a2=middle+1;
        //始终取最小纵坐标的形心点加入有序序列
        while(a1<=middle&&a2<=end) {
            if(list.get(a1).center.y<=list.get(a2).center.y) {
                listSorted.add(list.get(a1++));}
            else{
                listSorted.add(list.get(a2++));
            }
        }
        //剩余的形心点仍然是纵坐标有序的,直接加入即可
        if(a1<=middle) {listSorted.addAll(list.subList(a1,middle+1));}
        if(a2<=end) {listSorted.addAll(list.subList(a2,end+1));}
        //替换原区间为有序区间,参与下次合并
        for(int i=start;i<=end;++i){
            list.set(i,listSorted.get(i-start));
        }
        //3:建立中心区域[midX-H,midX+H]
        double bLeft = midX-dist;
        double bRight = midX+dist;
        ArrayList<MixPolygon> listInAreaH = new ArrayList<>(listSorted.stream()
                .filter(i->(i.center.x>=bLeft)&&(i.center.x<=bRight)).toList());
        //4:区域内形心遍历(每个形心范围内不超过5个有效点)
        for(int i=0;i<listInAreaH.size();++i) {
            for(int j=i+1;j<listInAreaH.size()&&
                    listInAreaH.get(j).center.y<=listInAreaH.get(i).center.y+dist;++j){
                dist = updateDistInMerge(dist,
                        Distance.betweenPolygon(listInAreaH.get(i),listInAreaH.get(j)));
            }
        }
        return Math.max(dist,0.0);
    }
}
