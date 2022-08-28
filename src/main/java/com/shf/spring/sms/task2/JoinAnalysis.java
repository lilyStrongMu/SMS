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
        Set<CheckNode> checkNodeSet = new HashSet<>();
        Map<String, String> tableAliasMap = new HashMap<>();
        Set<String> curTable_count = new HashSet<>();

        String sql =Sql.get(0).split(":")[1];
        String[] curTable_Theor=Sql.get(1).split(":")[1].split(",");
        //String sql = String.join(" ", Sql);//自测时用到
        System.out.println("sql:" + sql);

        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect) selectBody;
            //获取表名-表别名
            Table table = (Table) plainSelect.getFromItem();
            curTable_count.add(table.getName());
            if (table.getAlias() != null) {
                tableAliasMap.put(table.getAlias().getName(), table.getName());
            }

            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                //获取表名-表别名
                Table joinTable = (Table) join.getRightItem();
                curTable_count.add(joinTable.getName());
                if (joinTable.getAlias() != null) {
                    tableAliasMap.put(joinTable.getAlias().getName(), joinTable.getName());
                }
                //读取on语句并解析表名
                EqualsTo equalsTo = (EqualsTo) join.getOnExpressions().iterator().next();
                Column rightExpression = (Column) equalsTo.getRightExpression();
                Column leftExpression = (Column) equalsTo.getLeftExpression();

                String leftTableName = String.valueOf(leftExpression.getTable());
                if (tableAliasMap.containsKey(leftTableName)) {
                    leftTableName = tableAliasMap.get(leftTableName);
                }
                //String leftTableField = leftExpression.getColumnName();//A as a left join B on a.bid = B.id 中的bid
                String rightTableName = String.valueOf(rightExpression.getTable());
                if (tableAliasMap.containsKey(rightTableName)) {
                    rightTableName = tableAliasMap.get(rightTableName);
                }
                //更新权重
                int weight = getJoinWeight(join);
                if (weight == 0) {
                    System.out.println("非内联、左右外联和全外联类型的join语句，该语句暂不参与权重更新，如需要可进行功能扩展");
                } else {
                    CheckNode node = new CheckNode(leftTableName, rightTableName, weight);
                    checkNodeSet=addNode(checkNodeSet,node);
                }
                //该过程中步骤、结果打印
                System.out.println("join:" + join);
//                System.out.println("equalsTo:"+equalsTo);
//                System.out.println("rightExpression:"+rightExpression);
//                System.out.println("leftExpression:"+leftExpression);
                System.out.println("leftTableName:" + leftTableName);
                System.out.println("rightTableName:" + rightTableName);
                System.out.println("weight:" + weight);
                System.out.println("-----------------------------------------");

            }
            //table结果打印
            System.out.println("curTable_Theor"+ Arrays.toString(curTable_Theor));
            System.out.println("curTable_count" + curTable_count);
            System.out.println("tableAliasName&tableName" + tableAliasMap);

        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }

        //间接关系传递3次
        checkNodeSet = upgateSet(checkNodeSet, 3);

        return checkNodeSet;
    }

    public int getJoinWeight(Join join) {
        //权重计算
        int weight = 100;
        if (join.isInner() || join.isFull()) {
            return weight;
        } else if (join.isRight() || join.isLeft()) {
            return weight - 2;
        } else {
            return 0;
        }
    }


    public Set<CheckNode> upgateSet(Set<CheckNode> checkNodeSet, int n) {
        //间接关系传递,暴力递归，建议优化
        for (int i = 1; i < n; i++) {
            CheckNode[] CheckNodes = checkNodeSet.toArray(new CheckNode[0]);
            for (CheckNode node1 : CheckNodes) {
                for (CheckNode node2 : CheckNodes) {
                    if (!Objects.equals(node1, node2)) {
                        if (Objects.equals(node1.getName1(), node2.getName1()) && !Objects.equals(node1.getName2(), node2.getName2())) {
                            CheckNode newnode = new CheckNode(node1.getName2(), node2.getName2(), node1.getWeight() * node2.getWeight() / 100);
                            if(!checkNodeSet.contains(newnode)){
                                checkNodeSet=addNode(checkNodeSet,newnode);
                                //System.out.println(node1+" "+node2+" "+newnode);
                            }
                        } else if (Objects.equals(node1.getName1(), node2.getName2()) && !Objects.equals(node1.getName2(), node2.getName1())) {
                            CheckNode newnode = new CheckNode(node1.getName2(), node2.getName1(), node1.getWeight() * node2.getWeight() / 100);
                            if(!checkNodeSet.contains(newnode)){
                                checkNodeSet=addNode(checkNodeSet,newnode);
                                //System.out.println(node1+" "+node2+" "+newnode);
                            }

                        }
                    }
                }
            }
            checkNodeSet=removeRepeat(checkNodeSet);
//            System.out.println("第"+i+"次迭代 ");
//            System.out.println(checkNodeSet);
//            System.out.println("---------------------------------------");
        }
        return checkNodeSet;
    }
    public Set<CheckNode> removeRepeat(Set<CheckNode> checkNodeSet) {
        //去重
        CheckNode[] CheckNodes2 = checkNodeSet.toArray(new CheckNode[0]);
        for (int i=0;i< checkNodeSet.size();i++) {
            for (int j=i+1;j< checkNodeSet.size();j++) {
                if (Objects.equals(CheckNodes2[i].getName1(), CheckNodes2[j].getName2()) && Objects.equals(CheckNodes2[i].getName2(), CheckNodes2[j].getName1())) {
                    checkNodeSet.remove(CheckNodes2[i]);
                }
            }
        }
        return checkNodeSet;
    }
    public Set<CheckNode> addNode(Set<CheckNode> checkNodeSet,CheckNode node){
        if (checkNodeSet.contains(node)) {
            for (CheckNode t : checkNodeSet) {
                if (t.equals(node) && node.getWeight() < t.getWeight()) {
                    t.setWeight(node.getWeight());
                }
            }
        } else{
            checkNodeSet.add(node);
        }
        return checkNodeSet;
    }
}
