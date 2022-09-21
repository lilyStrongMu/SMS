package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import net.sf.jsqlparser.statement.Statement;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Relationship implements CheckSql {

    List<String> listOld = new ArrayList<>();
    List<String> listNew = new ArrayList<>();

    String left_name;
    String old_left_name;
    String right_name;
    String old_right_name;
    int weight;

    @Override
    public Set<CheckNode> getNewWeight(@NotNull List<String> Sql)
    {
        String sql_any = "";
        String tables = "";
        String[] tabel_array = null;
        String root_table = "";
        String[] son_table = null;
        int left_name_flag = 0,right_name_flag = 0;

        Set<CheckNode> checkNodeSet_Old = new HashSet<>();
        Set<CheckNode> checkNodeSet_Replace_Name = new HashSet<>();
        Set<CheckNode> checkNodeSet_New = new HashSet<>();

        //1.输入sql语句获得表名关系

        sql_any = Sql.get(0);
//        System.out.println(sql_any);

        tabel_names(sql_any);//获得新旧表名
//        System.out.println(listOld);
//        System.out.println(listNew);

        //2.输入sql，输出表与表别名关系。
        checkNodeSet_Old = split(sql_any);
//        System.out.println(checkNodeSet_Old);

        //3.拆分输入，如果有表的嵌套映射关系，获得嵌套映射关系
        if(Sql.size()>=3){
            tables = Sql.get(2);
            //处理映射关系
            if(tables == null){
//                System.out.println("sql语句格式有误");;
            }
            else{
                tabel_array = tables.split(":");
            }
            root_table = tabel_array[1];
            son_table = tabel_array[2].split(",");
        }

        //4.遍历Set<CheckNode>元素,将别名替换为table名
        if(listNew.size()>0) {
            for (CheckNode item : checkNodeSet_Old) {
                left_name = item.getName1();
                right_name = item.getName2();
                weight = item.getWeight();

                //遍历整个list，
                left_name_flag = 0;
                right_name_flag = 0;
                for (int i = 0; i < listNew.size(); i++) {
                    //用list中的每个元素和左右表
                    if (listNew.get(i).equals(left_name)) {
                        old_left_name = listOld.get(i);
                        left_name_flag = 1;
                    }
                    //新名字替换成旧名字
                    else if (listNew.get(i).equals(right_name)) {
                        old_right_name = listOld.get(i);
                        right_name_flag = 1;
                    }
                }
                //名字统一替换为old_left_name
                //如果没有别名，就用一致的名字
                if(left_name_flag != 1){
                    old_left_name = left_name;
                }
                if(right_name_flag != 1){
                    old_right_name = right_name;
                }
                CheckNode node = new CheckNode(old_left_name, old_right_name, weight);
                checkNodeSet_Replace_Name.add(node);
            }
        }

        else {
            checkNodeSet_Replace_Name = checkNodeSet_Old;
        }
        //5.遍历Set<CheckNode>元素,处理嵌套映射关系

        for(CheckNode item: checkNodeSet_Replace_Name)
        {
            left_name = item.getName1();
            right_name = item.getName2();
            weight = item.getWeight();

            //有嵌套映射关系需要处理
            if(Sql.size()>=3) {
                //name1 和 name2是否为root
                //如果name1为root
                if (left_name.equals(root_table) ) {
                    for (String s : son_table) {
                        CheckNode node = new CheckNode(s, right_name, weight);
                        checkNodeSet_New.add(node);
                    }
                }
                //如果name2为root
                else if (right_name.equals(root_table)) {
                    for (String s : son_table) {
                        CheckNode node = new CheckNode(left_name, s, weight);
                        checkNodeSet_New.add(node);
                    }
                }
                else {
                    CheckNode node = new CheckNode(left_name, right_name, weight);
                    checkNodeSet_New.add(node);
                }
            }
            //没有嵌套映射关系需要处理
            else {
                checkNodeSet_New = checkNodeSet_Replace_Name;
            }
        }

        return checkNodeSet_New;

    }

    //输入是sql句子，输出是{{"table_name1", "table_name2", weight}}
    //处理思想。
    //1.预处理
    //2.通过运算符获得要分析的对象
    //3.分析比较每个运算符两边的关系，判断是否要输出到结点关系。完成
    public Set<CheckNode> split(String s){
        //自定义所有运算符类型与权重;
        Set<CheckNode> checkNodeSet=new HashSet<>();
        String[] array = null;
        int tableIndex = 0;
        String temp_table_str = "", temp_relationShip_str = "";
        int relationshipIndex = 0;
        int[] mul_relationshipIndex = new int[20];//用一个数组保存运算符出现的下标
        int relation_num = 0;
        String table1 = "", table2 = "";
        String[] left_tabel = null, right_table = null;
        int weight = 0;

        //0.语句预处理
        if(s == null){
//            System.out.println("sql语句格式有误");;
        }
        else{
            array = s.split(" ");
        }

        //1.遍历用数组保存下运算符
        for(int i = 0 ; i < array.length ; i++){
            if("from".equals(array[i])){
                //保存from的下标
                tableIndex = i;
            }
            else if("=".equals(array[i])){
                mul_relationshipIndex[relation_num] = i;
                relation_num = relation_num + 1;
            }
            else if("!=".equals(array[i])){
                mul_relationshipIndex[relation_num] = i;
                relation_num = relation_num + 1;
            }
            else if(">".equals(array[i])){
                mul_relationshipIndex[relation_num] = i;
                relation_num = relation_num + 1;
            }
            else if("<".equals(array[i])){
                mul_relationshipIndex[relation_num] = i;
                relation_num = relation_num + 1;
            }
            else if(">=".equals(array[i])){
                mul_relationshipIndex[relation_num] = i;
                relation_num = relation_num + 1;
            }
            else if("<=".equals(array[i])){
                mul_relationshipIndex[relation_num] = i;
                relation_num = relation_num + 1;
            }
        }

        //2.拆分运算符两边的内容,获得关系与表名。
        for(int i = 0 ; i < relation_num ; i++) {
            //先判断
            table1 = array[mul_relationshipIndex[i] - 1];
            table2 = array[mul_relationshipIndex[i] + 1];
            left_tabel = table1.split("\\.");
            right_table = table2.split("\\.");
            //3.通过关系判断权重
            switch (array[mul_relationshipIndex[i]]) {
                case "=":
                    weight = 100;
                    break;
                case "!=":
                    weight = 98;
                    break;
                case ">":
                    weight = 96;
                    break;
                case "<":
                    weight = 94;
                    break;
                case ">=":
                    weight = 92;
                    break;
                case "<=":
                    weight = 90;
                    break;
                default:
                    break;
            }
            //4.输出关系到节点上
            CheckNode node=new CheckNode(left_tabel[0],right_table[0],weight);
            checkNodeSet.add(node);
        }



        return checkNodeSet;
    }

    //get table_name and nick_name
    //输入sql，输出成对的表名{table,nick;table,nick}
    //返回值应该是个hash表，参考前面的hash表
    //但是这个具体的输出我还是不知道。
    public void tabel_names(String s) {
        String[] array = null;
        String[] array_table = null;
        String lower_s = "";
        String replace_s = "";
        String any_s = "";

        int idx_from,idx_on,idx_sub_on;

        //0.语句预处理
        if(s == null){
//            System.out.println("sql语句格式有误");;
        }
        lower_s = s;
        //全部的where改成on
        //全部的using改成on
        replace_s = lower_s.replace("where","on");
        replace_s = replace_s.replace("using","on");
        replace_s = replace_s.replace("WHERE","on");
        replace_s = replace_s.replace("USING","on");

        //全部的inner join、join、left join、right join全部变成","
        replace_s = replace_s.replace("inner join",",");
        replace_s = replace_s.replace("left join",",");
        replace_s = replace_s.replace("right join",",");
        replace_s = replace_s.replace("INNER JION",",");
        replace_s = replace_s.replace("LEFT JOIN",",");
        replace_s = replace_s.replace("RIGHT JOIN",",");
        replace_s = replace_s.replace("join",",");
        replace_s = replace_s.replace("JOIN",",");

        //AS改成as
        replace_s = replace_s.replace("AS","as");

        //2.截取我需要的部分
        //获得from到on的之间的字符串
        idx_from = replace_s.indexOf("from");
        idx_on = replace_s.lastIndexOf("on");
        //截取中间的string
        any_s = replace_s.substring(idx_from+5,idx_on-1);
        //3.按照","分割字符
        if(any_s == null){
//            System.out.println("sql语句格式有误");;
        }
        else{
            array = any_s.split(",");
        }
//        A as a
//        C on A.cid = C.id
//        student S
//        faculty F
        //按照" "划分为两个，如果有的话。完成我们的table对应。
        for(int i = 0 ; i < array.length ; i++) {
            array[i] = array[i].trim();
            //正则匹配一下，如果有as就用as的方法处理一下
            //        A as a
            if (array[i].matches("(.*)as(.*)")) {
//                add couple table
                array_table = array[i].split("as");
                array_table[0] = array_table[0].trim();
                array_table[1] = array_table[1].trim();
                listOld.add(array_table[0]);
                listNew.add(array_table[1]);
            }
            //还有没有on，有on的还要丢掉on后面的
            //        C on A.cid = C.id
            else if(array[i].matches("(.*)on(.*)")){
                idx_sub_on = array[i].indexOf("on");
                array[i] = array[i].substring(0,idx_sub_on);
                array[i] = array[i].trim();
                array_table = array[i].split(" ");
                if(array_table.length>=2)
                {
//                  add couple table name.
                    array_table[0] = array_table[0].trim();
                    array_table[1] = array_table[1].trim();
                    listOld.add(array_table[0]);
                    listNew.add(array_table[1]);
                }

            }
            //这是我们正常处理的情况
            //        faculty F
            else{
                array_table = array[i].split(" ");
                if(array_table.length>=2)
                {
//                  add couple table name.
                    array_table[0] = array_table[0].trim();
                    array_table[1] = array_table[1].trim();
                    listOld.add(array_table[0]);
                    listNew.add(array_table[1]);
                }
            }
        }
    }


}
