package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;

import java.util.*;

public class Layer {
    TreeMap<String, List<String>> map;
    Join join;
    Relationship re;

    public Layer(TreeMap<String, List<String>> map){
        this.map = map;
        this.join = new Join();
        this.re = new Relationship();
    }

    public Map<String, List<String>> getLast(){
        assert map != null;
        TreeMap<String, List<String>> lastMap = new TreeMap<>();
        for(Map.Entry<String, List<String>> entry: map.entrySet()){
            String key = entry.getKey();
            List<String> value = entry.getValue();
            assert join != null;
            Set<CheckNode> checkJoin = join.getNewWeight(value);
            assert re != null;
            Set<CheckNode> checkRe = re.getNewWeight(value);
            List<CheckNode> merge = new ArrayList<>();
            for(CheckNode t: checkJoin){
                for(CheckNode t1: checkRe){
                    if(t.equals(t1)){
                        if(t.getWeight() >= t1.getWeight()){
                            merge.add(t);
                        }else{
                            merge.add(t1);
                        }
                    }
                }
            }
            if(key.contains("1")){
                continue;
            }

        }
        return lastMap;
    }
}
