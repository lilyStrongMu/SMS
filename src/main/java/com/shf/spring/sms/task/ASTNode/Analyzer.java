package com.shf.spring.sms.task.ASTNode;

public interface Analyzer {
    /**
     * @param sql sql语句
     * @return sql语法树
     */
    AST analyze(String sql);
}
