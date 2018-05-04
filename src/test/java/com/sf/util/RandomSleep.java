package com.sf.util;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * thread not safe
 * User: qusifan
 * Date: 2018/5/4
 * Time: 上午11:41
 */
public class RandomSleep {
    static  Random r = new Random();

    public static void sleep(){
        try{
            Thread.sleep(r.nextInt(300));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
