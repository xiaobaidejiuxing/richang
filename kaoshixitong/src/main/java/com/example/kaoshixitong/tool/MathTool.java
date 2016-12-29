package com.example.kaoshixitong.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by qqr on 2016/12/28.
 */

public class MathTool {
    private static Random random = new Random() ;
    public static List<Integer> getRandomIndexs(int num, int max){
        if(num > max){
            throw new RuntimeException("num必须小于等于max");
        }
        List<Integer> indexs = new ArrayList<>() ;
        Map<Integer, Boolean> mapper = new HashMap<>() ;
        while(indexs.size() < num){
            int i = random.nextInt(max) ;
            if(mapper.get(i) == null){
                indexs.add(i) ;
                mapper.put(i, Boolean.TRUE) ;
            }
        }
        return indexs ;
    }
}
