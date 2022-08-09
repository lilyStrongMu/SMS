package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

public class JSqlParseAst implements AST{
    @Override
    public SqlTypes getSqlType() {
        return null;
    }

    @Override
    public String getSql() {
        return null;
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
        return null;
    }

    @Override
    public List<Column> getColumns() {
        return null;
    }

    @Override
    public List<Join> getJoins() {
        return null;
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
