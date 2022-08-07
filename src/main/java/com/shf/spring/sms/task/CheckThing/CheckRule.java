package com.shf.spring.sms.task.CheckThing;

import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;

import java.util.List;

public interface CheckRule {
    Report match(AST tree);

    /**
     * 规则作用域，SELECT、DELETE等
     */
    List<SqlTypes> scope();
}
