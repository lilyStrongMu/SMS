package com.shf.spring.sms.task2;

import java.util.*;

public class InputAdpter {
    public static TreeMap<String, List<String>> change(){
        TreeMap<String, List<String>> map = new TreeMap<>();
//        List<String> list10 = new ArrayList<>();
//        list10.add("sql0:select locationNO,classRoomNo,classSize from curriculum left join class on curriculum.courseNO = course.courseID left join class on curriculum.classNO = class.classNO");
//        list10.add("curTable:curriculum,course,class");
//        List<String> list11 = new ArrayList<>();
//        list11.add("sql0:select schoolYear, courseName from curriculum, course where curriculum.hour > course.hour");
//        list11.add("curTable:curriculum,course");
//        List<String> list20 = new ArrayList<>();
//        list20.add("sql1:select rank() over (order by temp1.classSize) number, temp1.classRoomNO, location.locationName  from  temp1, location where temp1.locationNO = location.locationNO");
//        list20.add("curTable:location");
//        list20.add("tables:temp1:curriculum,course,class");
//        List<String> list30 = new ArrayList<>();
//        list30.add("sql1:select temp2.number, classRoom.name from temp2, classRoom where temp2.classRoomNO = classRoom.classRoomNO and classRoom.locationNO in");
//        list30.add("curTable:classRoom");
//        list30.add("tables:temp2:curriculum,course,class,location");
//        list30.add("in:select locationNO from location where locationNO > 100");
//        map.put("1_0", list10);
//        map.put("1_1", list11);
//        map.put("2_0", list20);
//        map.put("3_0", list30);
        List<String> list10 = new ArrayList<>();
        list10.add("sql1:select t1.num, t2.price from order t1, goods t2 where t1.order_id = t2.goods_id");
        list10.add("curTable:order,goods");
        map.put("1_0", list10);
        return map;
    }
}
