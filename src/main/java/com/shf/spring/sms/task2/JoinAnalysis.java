package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class JoinAnalysis implements CheckSql {
    @Override
    public Set<CheckNode> getNewWeight(List<String> Sql) {
        Set<CheckNode> checkNodeSet=new HashSet<>();
        //String sql = Sql.toString();
        String sql ="select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id";
        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect) selectBody;
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                System.out.println(join);
                EqualsTo equalsTo = new EqualsTo();
                Column leftExpression = (Column) equalsTo.getLeftExpression();
                Column rightExpression = (Column) equalsTo.getRightExpression();
                String leftTableName = String.valueOf(leftExpression.getTable());//A as a left join B on a.bid = B.id 中的a
                //String leftTableField = leftExpression.getColumnName();//A as a left join B on a.bid = B.id 中的bid
                String rightTableName = String.valueOf(rightExpression.getTable());//A as a left join B on a.bid = B.id 中的B
                //String rightTableField = rightExpression.getColumnName();//A as a left join B on a.bid = B.id 中的id
                /*String joinType=join.toString();
                getJoinWeight(joinType,leftTableName,rightTableName);*/

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

            }
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
