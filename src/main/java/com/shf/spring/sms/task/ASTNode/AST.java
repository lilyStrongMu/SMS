package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

public interface AST {
    SqlTypes getSqlType();

    String getSql();

    Expression getWhere();

    GroupByElement getGroupBy();

    List<SelectItem> getSelects();

    List<Column> getColumns();

    List<Join> getJoins();

    Limit getLimit();

    List<OrderByElement> getOrderByElement();



}
