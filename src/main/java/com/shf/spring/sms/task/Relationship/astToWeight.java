package com.shf.spring.sms.task.Relationship;
import com.shf.spring.sms.task.Relationship.DatabaseTable;
import com.shf.spring.sms.task.Relationship.GenerateTable;


import java.util.HashMap;


public class astToWeight {
    public HashMap<String, Integer> TableWeightMap = new HashMap<>();

    public static void fillGenerateCols(){
        //调用ast分析的结果，填充ContainsRelationMap
    }
    public static void fillContainsRelationMap(String col,String[] sourcecols){
        //调用ast分析的结果，填充ContainsRelationMap
    }

    public static void GetTableRelationWeight(String col,String[] sourcecols){
        //将ContainsRelationMap中的参数输入，用于生成权重树并更新全局的表关联关系TableWeightMap
    }


}
