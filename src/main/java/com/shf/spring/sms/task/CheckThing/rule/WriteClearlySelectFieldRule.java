package com.shf.spring.sms.task.CheckThing.rule;

import net.sf.jsqlparser.statement.select.SelectVisitor;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.statement.select.*;

import java.util.Arrays;
import java.util.List;

/**
 *  禁止使用 SELECT *
 */

public class WriteClearlySelectFieldRule implements CheckRule{
    @Override
    public Report match(AST tree)
    {
        Report report = new Report(tree.getSql());
//        // 抛出异常
//        throw new RuntimeException("异常");
        report.setPass(true);

        List<SelectItem> selectItems = tree.getSelects();
        //查询体中是否有*号
        if(checkAsterisk(selectItems)){
            report.setDesc("请写明查询字段，不要使用select *");
            report.setPass(false);
            report.setLevel(Report.Level.ERROR);
            return report;
        }

        //join子查询中是否有*号，有则报错
        List<Join> joins = tree.getJoins();
        if(joins == null || joins.size() <1){
            return report;
        }

        for(Join join : joins){
            //如果是子查询
            if(join.getRightItem() instanceof SubSelect){
                //获取子查询
                SelectBody selectBody = ((SubSelect) join.getRightItem()).getSelectBody();
                if(selectBody instanceof PlainSelect){
                    //检查是否有*号
                    if(checkAsterisk(((PlainSelect) selectBody).getSelectItems())){
                        report.setDesc("请写明查询字段，不要使用select *");
                        report.setPass(false);
                        report.setLevel(Report.Level.ERROR);
                        return report;
                    }
                }
            }
        }

        //where子查询中是否有*号
        Expression where = tree.getWhere();
        ExpressionVisitorAdapter adapter = new ExpressionVisitorAdapter();
        adapter.setSelectVisitor( new MySelectVisitor(report));
        where.accept(adapter);
        return report;
    }

    private boolean checkAsterisk(List<SelectItem> selectItems) {
    }

    @Override
    public List<SqlTypes> scope()
    {
        return Arrays.asList(SqlTypes.SELECT);
    }
}
