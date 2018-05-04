package com.sf.service;

import com.sf.util.RandomSleep;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:40
 */
public class SecondService {


    private ThirdService thirdService;

    public SecondService(){
        thirdService = new ThirdService();
    }


    public void secondMethod(String name,String hehe){
        System.out.println("secondMethod."+name);
        thirdService.thirdMethod("aa","cc");
        RandomSleep.sleep();
    }

}
