package com.sf;

import com.sf.core.MyTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:19
 */
public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MyTransformer());
    }

}
