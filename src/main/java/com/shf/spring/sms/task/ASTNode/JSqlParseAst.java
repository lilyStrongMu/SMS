package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;

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
        } else if (statement instanceof Update) {
            return SqlTypes.UPDATE;
        } else if (statement instanceof Delete) {
            return SqlTypes.DELETE;
        } else if (statement instanceof Insert) {
            return SqlTypes.INSERT;
        } else if (statement instanceof Replace) {
            return SqlTypes.REPLACE;
        } else if(statement instanceof GrammarErrStatement){
            return SqlTypes.ERROR;
        }
        else {
            return SqlTypes.OTHER;
        }
    }

    @Override
    public String getSql() {
        return this.sql;
    }

    @Override
    public Expression getWhere() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                System.out.println("1111111");
                return ((PlainSelect) select.getSelectBody()).getWhere();
            case UPDATE:
                Update update = (Update) statement;
                return update.getWhere();
            case DELETE:
                Delete delete = (Delete) statement;
                return delete.getWhere();
            default:
                return null;
        }
    }

    @Override
    public GroupByElement getGroupBy() {
            switch (this.getSqlType()) {
                case SELECT:
                    Select select = (Select) statement;
                    return  ((PlainSelect) select.getSelectBody()).getGroupBy();
                default:
                    return null;
            }
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

    @Override
    public List<Column> getColumns() {
        switch (this.getSqlType()) {
            case REPLACE:
                Replace replace = (Replace) statement;
                return replace.getColumns();
            case UPDATE:
                Update update = (Update) statement;
                return update.getColumns();
            case INSERT:
                Insert insert = (Insert) statement;
                return insert.getColumns();
            default:
                return null;
        }
    }

    @Override
    public List<Join> getJoins() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return ((PlainSelect) select.getSelectBody()).getJoins();
            case DELETE:
                Delete delete = (Delete) statement;
                return delete.getJoins();
            case UPDATE:
                Update update = (Update) statement;
                return update.getJoins();
            default:
                return null;
        }
    }

    @Override
    public Limit getLimit() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return ((PlainSelect) select.getSelectBody()).getLimit();
            case DELETE:
                Delete delete = (Delete) statement;
                return delete.getLimit();
            case UPDATE:
                Update update = (Update) statement;
                return update.getLimit();
            default:
                return null;
        }
    }

    @Override
    public List<OrderByElement> getOrderByElement() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return ((PlainSelect) select.getSelectBody()).getOrderByElements();
            case DELETE:
                Delete delete = (Delete) statement;
                return delete.getOrderByElements();
            case UPDATE:
                Update update = (Update) statement;
                return update.getOrderByElements();
            default:
                return null;
        }
    }
}

