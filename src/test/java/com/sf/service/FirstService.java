package com.sf.service;

import com.sf.util.RandomSleep;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:40
 */
public class FirstService {

    private SecondService secondService;

    public FirstService(){
        secondService = new SecondService();
    }


    public void firstMethod(String xixixhahaha,String hehe){
        System.out.println("firstMethod."+xixixhahaha);
        secondService.secondMethod("bb","cc");
        RandomSleep.sleep();
    }


}
