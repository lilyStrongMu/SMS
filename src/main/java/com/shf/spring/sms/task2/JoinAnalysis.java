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
//        @Test
//        public void listString() {
//            List<String> list = new ArrayList<>();
//            list.add("11");
//            list.add("22");
//            System.out.println("List :" + JSON.toJSONString(list));
//            System.out.println("String :" + StringUtils.join(list.toArray(), ","));
//        }
        String sql =String.join(" ",Sql);

        //String sql ="select * from A as a left join B AS b on a.bid = b.id inner join C AS c on A.cid = c.id right join Dd AS dd on B.did = dd.id";
        //String sql ="select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id";
        System.out.println("sql语句："+sql);

        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect) selectBody;
            //获取表名-表别名
            Table table = (Table)plainSelect.getFromItem();
            if(table.getAlias().getName() != null){
                tableAliasMap.put(table.getAlias().getName(),table.getName());
            }

            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                System.out.println("join语句:"+join);
                //获取表名-表别名
                Table joinTable=(Table)join.getRightItem();
                if(joinTable.getAlias().getName() != null){
                    tableAliasMap.put(joinTable.getAlias().getName(),joinTable.getName());
                }

                //String rightTableName = String.valueOf(rightTable.getName());//原始表名
                //String leftTableName=rightTableName;
                //Collection<Expression> Collection = join.getOnExpressions();

                //System.out.println("join.getOnExpressions():"+join.getOnExpressions().iterator().next());
                EqualsTo equalsTo = (EqualsTo) join.getOnExpressions().iterator().next();
                System.out.println("equalsTo:"+equalsTo);
                Column rightExpression = (Column) equalsTo.getRightExpression();
                System.out.println("rightExpression:"+rightExpression);
                Column leftExpression = (Column) equalsTo.getLeftExpression();
                System.out.println("leftExpression:"+leftExpression);

                String leftTableName = String.valueOf(leftExpression.getTable());//A as a left join B on a.bid = B.id 中的a
                if(tableAliasMap.containsKey(leftTableName)){
                    leftTableName=tableAliasMap.get(leftTableName);
                }
                //String leftTableField = leftExpression.getColumnName();//A as a left join B on a.bid = B.id 中的bid
                String rightTableName = String.valueOf(rightExpression.getTable());//A as a left join B on a.bid = B.id 中的B
                if(tableAliasMap.containsKey(rightTableName)){
                    rightTableName=tableAliasMap.get(rightTableName);
                }
                String rightTableField = rightExpression.getColumnName();//A as a left join B on a.bid = B.id 中的id

                System.out.println("leftTableName:"+leftTableName);
                System.out.println("rightTableName:"+rightTableName);

                int weight=getJoinWeight(join);
                System.out.println("weight:"+weight);
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

            }
            System.out.println("表别名&表名"+tableAliasMap);
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

/*
    // 获取表及对应的别名
    public void testParseAlias(){
        Map<String,String> map = new HashMap<>();
        String sql = "select *from A as a left join B b on a.bid = B.id left join C on A.cid = C.id and A.m = 3 left join D on B.did = D.id where a.id = 23 or b.id = 34 or c.id = 54";
        try {
            Select select = (Select)CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect)selectBody;
            Table table = (Table)plainSelect.getFromItem();
            if(table.getAlias() != null){
                map.put(table.getAlias().getName(),table.getName());
            }

            for(Join join : plainSelect.getJoins()){
                Table table1 = (Table)join.getRightItem();
                if(table1.getAlias()!=null){
                    map.put(table1.getAlias().getName(),table1.getName());
                }
            }
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
        }

 */
//                FromItem rightItem = join.getRightItem();
//                FromItem leftItem = join.getLeftItem();
//                Table rightTable=(Table)rightItem;
//                Table leftTable=(Table)leftItem;
//                String rightTableName = String.valueOf(rightTable.getName());
//                String leftTableName = String.valueOf(leftTable.getName());