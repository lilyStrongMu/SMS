package com.shf.spring.sms.task.CheckThing.rule;

import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;

import java.util.List;

/**
 *  禁止使用 SELECT *
 */

public class WriteClearlySelectFieldRule implements CheckRule{
    @Override
    public Report match(AST tree) {
//        Report report = new Report(tree.getSql());
        // 抛出异常
        throw new RuntimeException("异常");
    }

    @Override
    public List<SqlTypes> scope()
    {
        return null;
    }
}
