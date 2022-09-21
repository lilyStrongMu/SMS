package com.shf.spring.sms.task.ASTNode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSqlParseAstTest {
    Analyzer analyzer = new JSqlParseAnalyzer();
    String sqlString = "select b1 from b where b.b2>100";
    AST tree = analyzer.analyze(sqlString);
    @Test
    void getTable() {
        System.out.println(tree.getTable());
    }
}