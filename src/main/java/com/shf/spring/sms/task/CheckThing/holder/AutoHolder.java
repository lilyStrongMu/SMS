package com.shf.spring.sms.task.CheckThing.holder;

import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.CheckThing.check.Checker;
import com.shf.spring.sms.task.CheckThing.rule.CheckRule;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class AutoHolder {
    static {
        System.out.println("I am AutoHolder");
        ServiceLoader<Checker> checkers = ServiceLoader.load(Checker.class);
        for (Checker checker : checkers) {
            CheckerHolder.registeChecker(checker);
        }
        ServiceLoader<CheckRule> services = ServiceLoader.load(CheckRule.class);
        for (CheckRule rule : services) {
            List<SqlTypes> scopes = rule.scope();
            for (SqlTypes scope : scopes) {
                CheckerHolder.getCheckers().get(scope.toString()).registeRule(rule);
            }
        }
    }
}
