package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

public class JSqlParseAst implements AST{

    private Statement statement;
    private String sql;

    public JSqlParseAst(Statement statement, String sql) {
        this.statement = statement;
        this.sql = sql;
    }

    @Override
    public SqlTypes getSqlType() {
        if (statement instanceof Select) {
            return SqlTypes.SELECT;
        }
        else {
            return null;
        }
    }

    @Override
    public String getSql()
    {
        return this.sql;
    }

    @Override
    public Expression getWhere() {
        return null;
    }

    @Override
    public GroupByElement getGroupBy() {
        return null;
    }

    @Override
    public List<SelectItem> getSelects() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return ((PlainSelect) select.getSelectBody()).getSelectItems();
            default:
                return null;
        }
    }

//    @Override
//    public List<SelectItem> nest_getSelects() {
//        switch (this.getSqlType()) {
//            case SELECT:
//                Select select = (Select) statement;
//                return ((PlainSelect) select.getSelectBody()).getSelectItems();
//            default:
//                return null;
//        }
//    }

    @Override
    public List<Column> getColumns() {
        return null;
    }

    @Override
    public List<Join> getJoins() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return ((PlainSelect) select.getSelectBody()).getJoins();
            default:
                return null;
        }

    }

    @Override
    public Limit getLimit() {
        return null;
    }

    @Override
    public List<OrderByElement> getOrderByElement() {
        return null;
    }
}
