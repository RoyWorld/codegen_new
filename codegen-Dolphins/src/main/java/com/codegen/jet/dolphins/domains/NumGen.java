package com.codegen.jet.dolphins.domains;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 14-9-30 下午11:29<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class NumGen {
    private static final String[] high = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v","w","x"};
    private static final String[] low = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final Map<Integer, String> maps = new HashMap<Integer, String>();
    private static final AtomicInteger incr = new AtomicInteger(0);

    static{
        for(String h : high){
            for(String l : low){
                maps.put(incr.incrementAndGet(), h+l);
            }
        }
    }

    public static String genNum(Integer id){
        return maps.get(id);
    }

    /**
     * 没做保护，请勿模仿
     * @return
     */
    public static Map<Integer, String> getMaps(){
        return maps;
    }

}
