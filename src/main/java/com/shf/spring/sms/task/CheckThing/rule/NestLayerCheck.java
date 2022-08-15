package com.shf.spring.sms.task.CheckThing.rule;

import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.statement.select.*;

import java.util.Arrays;
import java.util.List;

/**
 *  嵌套层次查询:关联查询的表超过5个，嵌套查询层次不得超过5层
 */

public class NestLayerCheck implements CheckRule{
    @Override
    public Report match(AST tree) {
        Report report = new Report(tree.getSql());


        report.setPass(true);


        List<SelectItem> selectItems = tree.getSelects();

        String[] num_Strings = report.sql.split("from");
        if (num_Strings.length - 1 > 5) {
//            int lastIndex = report.sql.lastIndexOf("select");
//            String new_list = report.sql.substring(lastIndex + 1);
//            List<String> newlist = Arrays.asList(new_list.split(" "));
            //查询嵌套体中是否有*号
//            if (checknestAsterisk(newlist)) {
                System.out.println("system: ‘from’ 嵌套语句查询层次不得超过 5 层");
//                report.setDesc("请写明查询字段，不要使用select *");
//            11
                report.setPass(false);
                report.setLevel(Report.Level.ERROR);
                return report;
//            }
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

        return report;

    }

    // 一般结构，检查提取到的语句语法
    private boolean checkAsterisk(List<SelectItem> selectItems) {

        if (selectItems.toString().contains("*")) {
            return true;
        } else {
            return false;
        }
    }
    // 嵌套结构，检查提取到的语句语法
    private boolean checknestAsterisk(List<String> newlist) {



        if (newlist.contains("*")) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public List<SqlTypes> scope() {
        return Arrays.asList(SqlTypes.SELECT);
    }
}
