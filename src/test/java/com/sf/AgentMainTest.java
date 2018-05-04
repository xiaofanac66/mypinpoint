package com.sf;

import com.sf.service.FirstService;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 上午9:26
 */
public class AgentMainTest {

    public static void main(String[] args) {
        FirstService firstService = new FirstService();
        firstService.firstMethod("dfs","ddd");
    }

}
