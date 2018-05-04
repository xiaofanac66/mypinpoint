package com.sf.plugins.threadlocal;

import com.sf.aop.AopInterceptor;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:56
 */
public class ThreadLocalPlugin {

    private static ThreadLocal<LinkedList<AopInterceptor.Span>> LOCAL_SPAN = new ThreadLocal<>();

    public static void trace(Object ... params){
        String methodName = params[0].toString();
        LinkedList<AopInterceptor.Span> spans = LOCAL_SPAN.get();
        AopInterceptor.Span parentSpan;
        if(spans == null){
            spans = new LinkedList<>();
            parentSpan = new AopInterceptor.Span(0,1,System.currentTimeMillis(),methodName,params);
            spans.push(parentSpan);
            LOCAL_SPAN.set(spans);
        }else{
            AopInterceptor.Span last = spans.getFirst();
            AopInterceptor.Span currentSpan = new AopInterceptor.Span(last.getCurrentSpanId(),last.getCurrentSpanId() + 1,
                    System.currentTimeMillis(),methodName,params);
            spans.push(currentSpan);
        }
    }

    public static void afterInvoke(){
        LinkedList<AopInterceptor.Span> spans = LOCAL_SPAN.get();
        AopInterceptor.Span span = spans.pop();
        if(span.getCurrentSpanId() == 1){
            LOCAL_SPAN.remove();
        }
        formatPrintLn(span);
    }

    private static String formatString = "Method:%s finished,ParentId:%d,CurrentId:%d,UseTime:%d Param:";

    private static void formatPrintLn(AopInterceptor.Span span){
        StringBuilder sb = new StringBuilder(String.format(formatString,span.getMethodName(),span.getParentSpanId(),span.getCurrentSpanId(),
                System.currentTimeMillis() - span.getStartTime()));
        Object[] params = span.getParams();
        for (int i = 1; i < params.length; i++) {
            sb.append("["+(i-1)+"]").append(params[i]).append(";");
        }
        System.out.println(sb.toString());
    }
}
