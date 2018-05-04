package com.sf.plugins.aop;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 下午5:09
 */
public class AopInterceptor {

    private static class Span{
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

    private static ThreadLocal<LinkedList<Span>> LOCAL_SPAN = new ThreadLocal<>();

    public static void beforeInvoke(Object ... names){
        String methodName = names[0].toString();
        LinkedList<Span> spans = LOCAL_SPAN.get();
        Span parentSpan;
        if(spans == null){
            spans = new LinkedList<>();
            parentSpan = new Span(0,1,System.currentTimeMillis(),methodName,names);
            spans.push(parentSpan);
            LOCAL_SPAN.set(spans);
        }else{
            Span last = spans.getFirst();
            Span currentSpan = new Span(last.currentSpanId,last.currentSpanId + 1,
                    System.currentTimeMillis(),methodName,names);
            spans.push(currentSpan);
        }
    }


    public static void afterInvoke(){
        LinkedList<Span> spans = LOCAL_SPAN.get();
        Span span = spans.pop();
        if(span.currentSpanId == 1){
            LOCAL_SPAN.remove();
        }
        formatPrintLn(span);
    }


    private static String formatString = "Method:%s finished,ParentId:%d,CurrentId:%d,UseTime:%d Param:";

    private static void formatPrintLn(Span span){
        StringBuilder sb = new StringBuilder(String.format(formatString,span.methodName,span.parentSpanId,span.currentSpanId,
                System.currentTimeMillis() - span.startTime));
        Object[] params = span.params;
        for (int i = 1; i < params.length; i++) {
            sb.append("["+(i-1)+"]").append(params[i]).append(";");
        }
        System.out.println(sb.toString());
    }


}
