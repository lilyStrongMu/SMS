package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;
import java.util.Map;

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

    List<String> getTable();

    Distinct getDistinct();

    int getNestedLayers();

    Map Items_Tables();
}
