package com.shf.spring.sms.task.ASTNode;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
//                Map map = test_select_subselect(select.getSelectBody());
//                System.out.println(map);
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
    public List<String> getTable() {
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return tablesNamesFinder.getTableList(select);
            case DELETE:
                Delete delete = (Delete) statement;
                return tablesNamesFinder.getTableList(delete);
            case UPDATE:
                Update update = (Update) statement;
                return tablesNamesFinder.getTableList(update);
            case INSERT:
                Insert insert = (Insert) statement;
                return tablesNamesFinder.getTableList(insert);
            case REPLACE:
                Replace replace = (Replace) statement;
                return tablesNamesFinder.getTableList(replace);
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

    @Override
    public Distinct getDistinct() {
        switch (this.getSqlType()) {
            case SELECT:
                Select select = (Select) statement;
                return ((PlainSelect) select.getSelectBody()).getDistinct();
            default:
                return null;
        }
    }

    @Override
    public int getNestedLayers() {
        int n = sql.length();
        int res = 0;
        char[] cs = sql.toCharArray();
        for (int i = 0; i<n ; i++){
            if(cs[i] == '(' && cs[i+1]=='S'){
                res ++;
            }
        }
        return res;
    }


    private static Map test_select_subselect(SelectBody selectBody){
        Map<String, String> map = new HashMap<String, String>();

        if (selectBody instanceof PlainSelect) {
            List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
            for (SelectItem selectItem : selectItems) {
                if (selectItem.toString().contains("(") && selectItem.toString().contains(")")) {
                    map.put("selectItemsSubselect", selectItem.toString());
                }
            }

            Expression where = ((PlainSelect) selectBody).getWhere();
            String whereStr = where.toString();
            if (whereStr.contains("(") && whereStr.contains(")")) {
                int firstIndex = whereStr.indexOf("(");
                int lastIndex = whereStr.lastIndexOf(")");
                CharSequence charSequence = whereStr.subSequence(firstIndex, lastIndex + 1);
                map.put("whereSubselect", charSequence.toString());
            }

            FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
            if (fromItem instanceof SubSelect) {
                map.put("fromItemSubselect", fromItem.toString());
            }

        } else if (selectBody instanceof WithItem) {
            test_select_subselect(((WithItem) selectBody).getSelectBody());
        }
        return map;
    }
}

