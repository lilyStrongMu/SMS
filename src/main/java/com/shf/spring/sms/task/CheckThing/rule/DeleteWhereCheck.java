package com.shf.spring.sms.task.CheckThing.rule;

import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;

import java.util.List;

public class DeleteWhereCheck implements CheckRule{
    @Override
    public Report match(AST tree) {
//        Report report = new Report(tree.getSql());
        throw new RuntimeException("未开发");
    }

    @Override
    public List<SqlTypes> scope() {
        return null;
    }
}
