package com.shf.spring.sms.task.CheckThing.rule;

import net.sf.jsqlparser.statement.select.SelectVisitor;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  禁止使用 SELECT *
 */

public class WriteClearlySelectFieldRule implements CheckRule {
    @Override
    public Report match(AST tree) {
        Report report = new Report(tree.getSql());

        // 检查提取到的语句语法（含嵌套检查）11
//        private boolean checkAsterisk(List<SelectItem> selectItems) {
//            String[] num_Strings = report.sql.split("select");
//            if (num_Strings.length - 1 > 1) {
//                int lastIndex = report.sql.lastIndexOf("select");
//                String new_list = report.sql.substring(lastIndex + 1);
//                if (new_list.contains("*")) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            if (selectItems.toString().contains("*")) {
//                return true;
//            } else {
//                return false;
//            }
//        }

//        // 抛出异常
//        throw new RuntimeException("异常");

        report.setPass(true);

//        List<SelectItem> nest_selectItems = tree.getSql();
//
//        // 检查提取到的语句语法（含嵌套检查）11
//        private boolean checkAsterisk(List<SelectItem> selectItems) {
//        String[] num_Strings = selectItems.toString().split("select");
//        if(num_Strings.length - 1 > 1) {
//            int lastIndex = selectItems.toString().lastIndexOf("select");
//            String new_list = selectItems.toString().substring(lastIndex + 1);
//            if (new_list.contains("*")) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        if (selectItems.toString().contains("*")) {
//            return true;
//        } else {
//            return false;
//        }
//    }

        List<SelectItem> selectItems = tree.getSelects();

        String[] num_Strings = report.sql.toLowerCase().split("select");
        if (num_Strings.length - 1 > 1) {
            int lastIndex = report.sql.lastIndexOf("select");
            String new_list = report.sql.substring(lastIndex + 1);
            List<String> newlist = Arrays.asList(new_list.split(" "));
            // 1. 查询嵌套体中是否有*号
            if (checknestAsterisk(newlist)) {
                System.out.println("system: 嵌套体内字段名请写明查询字段，嵌套体内不要使用 ‘select *’");
//                report.setDesc("请写明查询字段，不要使用select *");
//11
                report.setPass(false);
                report.setDesc("请写明查询字段，不要使用select *");
                report.setLevel(Report.Level.ERROR);
                return report;
            }
        }
        // 2. 查询一般体中是否有*号
        else if (checkAsterisk(selectItems)) {
            System.out.println("system: 请写明查询字段，不要使用 ‘select *’");
//            report.setDesc("请写明查询字段，不要使用select *");
// 1
            report.setPass(false);
            report.setDesc("请写明查询字段，不要使用select *");
            report.setLevel(Report.Level.ERROR);
            return report;
        }

//        //查询嵌套语句内层中是否有*号
//
//        if (checkAsterisk(selectItems)) {
//            System.out.println("system:请写明查询字段，不要使用select *");
//            report.setDesc("请写明查询字段，不要使用select *");
//            report.setPass(false);
//            report.setLevel(Report.Level.ERROR);
//            return report;
//        }

        //join子查询中是否有*号，有则报错
        List<Join> joins = tree.getJoins();
        if (joins == null || joins.size() < 1) {
            return report;
        }

        for (Join join : joins) {
            //如果是子查询
            if (join.getRightItem() instanceof SubSelect) {
                //获取子查询
                SelectBody selectBody = ((SubSelect) join.getRightItem()).getSelectBody();
                if (selectBody instanceof PlainSelect) {
                    //检查是否有*号
                    if (checkAsterisk(((PlainSelect) selectBody).getSelectItems())) {
                        report.setDesc("请写明查询字段，不要使用select *");
                        report.setPass(false);
                        report.setLevel(Report.Level.ERROR);

                        return report;
                    }
                }
            }
        }

        return report;

    }

    // 一般结构，检查提取到的语句语法
    private boolean checkAsterisk(List<SelectItem> selectItems) {
        if(selectItems == null || selectItems.size()==0){
            return true;
        }
        for(SelectItem item : selectItems){
            if(item.toString().contains("*")){
                return true;
            }
        }
        return false;
    }
    // 嵌套结构，检查提取到的语句语法
    private boolean checknestAsterisk(List<String> newlist) {

//        String[] num_Strings = report.sql.split("select");
//        if (num_Strings.length - 1 > 1) {
//            int lastIndex = report.sql.lastIndexOf("select");
//            String new_list = report.sql.substring(lastIndex + 1);

        return newlist.contains("*");
    }



//    // 检查提取到的语句语法（含嵌套检查）11
//    private boolean checkAsterisk(List<SelectItem> selectItems) {
//        String[] num_Strings = selectItems.toString().split("select");
//        if(num_Strings.length - 1 > 1) {
//            int lastIndex = selectItems.toString().lastIndexOf("select");
//            String new_list = selectItems.toString().substring(lastIndex + 1);
//            if (new_list.contains("*")) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        if (selectItems.toString().contains("*")) {
//            return true;
//        } else {
//            return false;
//        }
//    }

////        for (int i = 0; i < selectItems.size(); i++) {
////            String str = "";
//            int lastIndex = selectItems.toString().lastIndexOf("select");
////            int index1 = selectItems.get(i).indexOf(",");
////            System.out.println("第一个的索引：" + index1);
////            int index2 = list.get(i).indexOf(",", index1 + 1);
////            System.out.println("第二个的索引：" + index2);
//            //截取后面的字符串
//            String new_list = selectItems.toString().substring(lastIndex + 1);
////            System.out.println("新的字符串：" + new_list);
//
//            if (new_list.contains("*")) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        for (int i = 0; i < selectItems.size(); i++) {
//            String str = "";
//            int lastIndex = selectItems.toString().lastIndexOf("select");
////            int index1 = selectItems.get(i).indexOf(",");
////            System.out.println("第一个的索引：" + index1);
////            int index2 = list.get(i).indexOf(",", index1 + 1);
////            System.out.println("第二个的索引：" + index2);
//            //截取后面的字符串
//            String new_list = selectItems.toString().substring(lastIndex + 1);
//            System.out.println("新的字符串：" + new_list);
//        }

    @Override
    public List<SqlTypes> scope() {
        return Arrays.asList(SqlTypes.SELECT);
    }
}
