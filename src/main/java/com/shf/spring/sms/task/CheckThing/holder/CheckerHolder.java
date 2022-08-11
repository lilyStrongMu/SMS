package com.shf.spring.sms.task.CheckThing.holder;

import com.shf.spring.sms.task.CheckThing.check.Checker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CheckerHolder {
    private static Map<String, Checker> checkers = new ConcurrentHashMap<>(16);

    public static void registeChecker(Checker checker){
        checkers.putIfAbsent(checker.getName(),checker);
    }

    public static void unRegisteChecker(Checker checker){
        checkers.remove(checker.getName());
    }

    public static  Map<String,Checker> getCheckers(){
        return checkers;
    }

}
