package com.sf.plugins.threadlocal;

import com.sf.plugins.trace.Trace;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:56
 */
public class ThreadLocalPlugin {

    private static ThreadLocal<Trace> LOCAL_TRACE = new ThreadLocal<>();

    private static void trace(){
        Trace trace = LOCAL_TRACE.get();
        if(trace == null){
            trace = new Trace();
            LOCAL_TRACE.set(trace);
        }



    }

}
