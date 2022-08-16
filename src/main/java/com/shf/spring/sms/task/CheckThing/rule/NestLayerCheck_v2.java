package com.shf.spring.sms.task.CheckThing.rule;

import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  嵌套层次查询:关联查询的表超过5个，嵌套查询层次不得超过5层
 */

public class NestLayerCheck_v2 implements CheckRule{
    @Override
    // 含大小写以及层次判断
    public Report match(AST tree) {
        Report report = new Report(tree.getSql());

        report.setPass(true);

        List<SelectItem> selectItems = tree.getSelects();

        int num_Strings = report.sql.length();
        // 记录深度
        int deep = 0;
        // 左括号的位置记录
        report.setSql(tree.getSql());
        List<Integer> alldeep = new ArrayList<>();
        List<String> names = tree.getTable();
        if(names != null && names.size() > 4){
            report.setPass(false);
            report.setLevel(Report.Level.ERROR);
            report.setDesc("查询的表的个数不超过5个.");
        }
        char[] chararray = report.sql.toCharArray();
        for (char c : chararray) {
            // 找到 ( 就加1
            if (c == '(')
                deep++;
            // 找到 ) 就减1
            if (c == ')')
                deep--;
            // 记录出现过的深度
            alldeep.add(deep);
        }
        // 求出最大嵌套层数次数
        Collections.sort(alldeep);
        int maxdeep = (int)alldeep.stream().distinct().count();
        if (maxdeep > 0)
            maxdeep--;

//        if (num_Strings.length - 1 > 5) {
//            int lastIndex = report.sql.lastIndexOf("select");
//            String new_list = report.sql.substring(lastIndex + 1);
//            List<String> newlist = Arrays.asList(new_list.split(" "));
            //查询嵌套体中是否有*号
//            if (checknestAsterisk(newlist)) {
        if(maxdeep > 4){
            //System.out.printf("system: 检测到 ‘from’ 多层嵌套有 %d 层，嵌套语句查询层次不得超过 5 层", maxdeep + 1);

            report.setPass(false);
            report.setLevel(Report.Level.ERROR);

            report.setDesc((report.getDesc() == null ? "": report.getDesc())+"嵌套语句查询层次不得超过 5 层");
        }

        return report;
    }
//        //查询一般体中是否有*号
//        else if (checkAsterisk(selectItems)) {
//            System.out.println("system: 请写明查询字段，不要使用select *");
//            report.setDesc("请写明查询字段，不要使用select *");
//            report.setPass(false);
//            report.setLevel(Report.Level.ERROR);
//            return report;
//        }
//
//
//        //join子查询中是否有*号，有则报错
//        List<Join> joins = tree.getJoins();
//        if (joins == null || joins.size() < 1) {
//            return report;
//        }
//
//        for (Join join : joins) {
//            //如果是子查询
//            if (join.getRightItem() instanceof SubSelect) {
//                //获取子查询
//                SelectBody selectBody = ((SubSelect) join.getRightItem()).getSelectBody();
//                if (selectBody instanceof PlainSelect) {
//                    //检查是否有*号
//                    if (checkAsterisk(((PlainSelect) selectBody).getSelectItems())) {
//                        report.setDesc("请写明查询字段，不要使用select *");
//                        report.setPass(false);
//                        report.setLevel(Report.Level.ERROR);
//                        return report;
//                    }
//                }
//            }
//        }
//
//        return report;

    // 一般结构，检查提取到的语句语法


    @Override
    public List<SqlTypes> scope() {
        return Arrays.asList(SqlTypes.SELECT);
    }
}
