package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annoations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class VectorExample3 {

    //java.util.ConcurrentModificationException  遍历的过程中不能做更新
    private static void test1(Vector<Integer> v1){//foreach
        for (Integer i:v1) {
            if(i.equals(3)){
                v1.remove(i);
            }
        }
    }

    //java.util.ConcurrentModificationException
    private static void test2(Vector<Integer> v1){//iterator
        Iterator<Integer> iterator=v1.iterator();
        while (iterator.hasNext()){
            Integer i=iterator.next();
            if(i.equals(3)){
                v1.remove(i);
            }
        }
    }

    //success
    private static void test3(Vector<Integer> v1){

        for(int i=0;i<v1.size();i++){
            if(v1.get(i).equals(3)){
                v1.remove(i);
            }
        }

    }

    //java.util.ConcurrentModificationException
    private static void test4(List<Integer> v1){

        for (Integer i:v1) {
            if(i.equals(3)){
                v1.remove(i);
            }
        }

    }

    public static void main(String[] args) {
        List<Integer> list=new Vector<>();
        list.add(1);
        list.add(2);
        list.add(3);
        test4(list);
    }
}
