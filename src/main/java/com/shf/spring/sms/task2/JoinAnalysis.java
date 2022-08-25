package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

import java.util.*;


public class JoinAnalysis implements CheckSql {
    @Override
    public Set<CheckNode> getNewWeight(List<String> Sql) {
        Set<CheckNode> checkNodeSet=new HashSet<>();
        Map<String,String> tableAliasMap = new HashMap<>();
        Set<String> curTable_count = new HashSet<>();

        String sql =Sql.get(0).split(":")[1];
        String[] curTable_Theor=Sql.get(1).split(":")[1].split(",");
        System.out.println("sql:"+sql);

        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect) selectBody;
            //获取表名-表别名
            Table table = (Table)plainSelect.getFromItem();
            curTable_count.add(table.getName());
            if(table.getAlias()!= null){
                tableAliasMap.put(table.getAlias().getName(),table.getName());
            }

            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                //获取表名-表别名
                Table joinTable=(Table)join.getRightItem();
                curTable_count.add(joinTable.getName());
                if(joinTable.getAlias() != null){
                    tableAliasMap.put(joinTable.getAlias().getName(),joinTable.getName());
                }
                //读取on语句并解析表名
                EqualsTo equalsTo = (EqualsTo) join.getOnExpressions().iterator().next();
                Column rightExpression = (Column) equalsTo.getRightExpression();
                Column leftExpression = (Column) equalsTo.getLeftExpression();

                String leftTableName = String.valueOf(leftExpression.getTable());
                if(tableAliasMap.containsKey(leftTableName)){
                    leftTableName=tableAliasMap.get(leftTableName);
                }
                //String leftTableField = leftExpression.getColumnName();//A as a left join B on a.bid = B.id 中的bid
                String rightTableName = String.valueOf(rightExpression.getTable());
                if(tableAliasMap.containsKey(rightTableName)){
                    rightTableName=tableAliasMap.get(rightTableName);
                }
                //更新权重
                int weight=getJoinWeight(join);
                if(weight==0){
                    System.out.println("非内联、左右外联和全外联类型的join语句，该语句暂不参与权重更新，如需要可进行功能扩展");
                }else{
                    CheckNode node=new CheckNode(leftTableName,rightTableName,weight);
                    if(checkNodeSet.contains(node)) {
                        for(CheckNode t: checkNodeSet){
                            if (t.equals(node)&&weight<node.getWeight()){
                                checkNodeSet.add(node);
                            }
                        }
                    }else
                        checkNodeSet.add(node);
                }
                //该过程中步骤、结果打印
                System.out.println("join:"+join);
                System.out.println("equalsTo:"+equalsTo);
                System.out.println("rightExpression:"+rightExpression);
                System.out.println("leftExpression:"+leftExpression);
                System.out.println("leftTableName:"+leftTableName);
                System.out.println("rightTableName:"+rightTableName);
                System.out.println("weight:"+weight);
                System.out.println(" ");

            }
            //table结果打印
            System.out.println("curTable_Theor"+ Arrays.toString(curTable_Theor));
            System.out.println("curTable_count"+curTable_count);
            System.out.println("tableAliasName&tableName"+tableAliasMap);

        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
        return checkNodeSet;
    }
    public int getJoinWeight(Join join) {
        int weight=100;
        if (join.isInner()||join.isFull()) {
            return weight;
        }else if (join.isRight()||join.isLeft()) {
            return weight-2;
        }else{
            return 0;
        }
    }
}
