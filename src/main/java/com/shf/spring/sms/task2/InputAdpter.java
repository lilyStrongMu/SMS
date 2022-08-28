package com.shf.spring.sms.task2;

import java.util.*;

public class InputAdpter {
    public static TreeMap<String, List<String>> change(){
        TreeMap<String, List<String>> map = new TreeMap<>();
        List<String> list10 = new ArrayList<>();
        list10.add("sql0:select a1,b1,c1 from a left join b on a.a1 = b.b2 left join c on b.b3 = c.c1");
        list10.add("curTable:a,b,c");
        List<String> list11 = new ArrayList<>();
        list11.add("sql0:select a1,b2 from a,b where a.a1 > b.b1");
        list11.add("curTable:a,b");
        List<String> list20 = new ArrayList<>();
        list20.add("sql1:select rank() over (order by t.a1) number,count(t.b2),ttt.id,ttt.id from t,ttt where t.a1 = ttt.a1");
        list20.add("curTable:ttt");
        list20.add("tables:t:a,b,c");
        List<String> list30 = new ArrayList<>();
        list30.add("sql1:select t2.id,ftable.name from t2,ftable where t2.id = ftable.id and ftable.name in");
        list30.add("curTable:ftable");
        list30.add("tables:t2:a,b,c,ttt");
        list30.add("in:select b1 from b where b.b2 > 100");
        map.put("1_0", list10);
        map.put("1_1", list11);
        map.put("2_0", list20);
        map.put("3_0", list30);
        return map;
    }
}
