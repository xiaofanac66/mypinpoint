package com.sf.service;

import com.sf.util.RandomSleep;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:42
 */
public class ThirdService {

    public void thirdMethod(String name,String hehe){
        System.out.println("thirdMethod."+name);
        RandomSleep.sleep();
    }



//    public Integer getTest(String a,Object ... b){
//        return 0;
//    }
//
//    public static void main(String[] args) {
//        try {
//            Method getTest = ThirdService.class.getMethod("getTest", String.class, Object[].class);
//            System.out.println(getTest);
//            System.out.println(getTest);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
}
