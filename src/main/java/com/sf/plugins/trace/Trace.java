package com.sf.plugins.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午10:00
 */
public class Trace {

    public Trace(){
        traceId = UUID.randomUUID().toString();
        genSpan(1,1);
    }

    private String traceId;

    private LinkedList<Span> _SPANS = new LinkedList<>();

    private String mainFlag;

    public Span genSpan(Integer parentSpanId,Integer spanId){
        Span s = new Span(parentSpanId,spanId);
        _SPANS.add(s);
        return s;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    private static class Span{

        private Integer parentSpanId;

        private Integer spanId;
    }

}
