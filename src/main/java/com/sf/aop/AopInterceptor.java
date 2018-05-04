package com.sf.aop;

import com.sf.plugins.threadlocal.ThreadLocalPlugin;
import lombok.Data;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 下午5:09
 */
public class AopInterceptor {

    @Data
    public static class Span{
        int parentSpanId;
        int currentSpanId;
        long startTime;
        String methodName;
        Object[] params;

        public Span(int parentSpanId, int currentSpanId, long startTime ,String methodName,Object[] params) {
            this.parentSpanId = parentSpanId;
            this.currentSpanId = currentSpanId;
            this.startTime = startTime;
            this.methodName = methodName;
            this.params = params;
        }
    }

    public static void beforeInvoke(Object ... params){
         ThreadLocalPlugin.trace(params);
    }


    public static void afterInvoke(){
        ThreadLocalPlugin.afterInvoke();
    }

}
