package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class JSqlParseAnalyzer implements Analyzer{
    @Override
    public AST analyze(String sql) {
        JSqlParseAst ast = null;
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            ast = new JSqlParseAst(statement, sql);
        } catch (Exception e) {
            ast = new JSqlParseAst(new GrammarErrStatement(), sql);
        }
        return ast;
    }
}
