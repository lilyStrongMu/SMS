package com.shf.spring.sms.task.CheckThing.check;

import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.CheckThing.rule.CheckRule;
import com.shf.spring.sms.task.PrintResult.Report;

import java.util.ArrayList;
import java.util.List;

public abstract class Checker {
    /**
     * @return 规则检查器的名称
     */
    public abstract String getName();

    /**
     * 规则集
     */
    protected List<CheckRule> rules = new ArrayList<>();

    public void registeRule(CheckRule rule){
        this.rules.add(rule);
    }

    /**
     * @param tree 抽象语法树
     * @return 规则检查报告
     */
    public List<Report> check(AST tree){
        List<Report> reports = new ArrayList<>();
        for(CheckRule rule : rules){
            Report report = rule.match(tree);
            if (report != null){
                reports.add(report);
            }
        }
        return reports;
    }
}
