package com.shf.spring.sms.task.CheckThing.rule;

import cn.hutool.extra.spring.SpringUtil;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.expression.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public class DeleteWhereCheck implements CheckRule{
    private final JdbcTemplate jdbcTemplate ;
    public DeleteWhereCheck(){
        jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
    }
    /**
     *
     * @param tree: 语法树
     * @return Report: 返回报告
     */
    @Override
    public Report match(AST tree) {
        Report report = new Report();
        report.setPass(true);
        report.setSql(tree.getSql());
        Expression expressionsWhere =  tree.getWhere();
        if(expressionsWhere == null){
            report.setDesc("请添加where字段");
            report.setLevel(Report.Level.ERROR);
            report.setPass(false);
        }else{
            String sql = tree.getSql();
            String tableName = tree.getTable().get(0);
            String indexSql = "SELECT\n" +
                    "\tINDEX_COLUMN \n" +
                    "FROM\n" +
                    "\t( SELECT TABLE_NAME, INDEX_NAME, GROUP_CONCAT( COLUMN_NAME ) AS INDEX_COLUMN FROM information_schema.statistics WHERE table_schema = 'sms' GROUP BY TABLE_NAME, INDEX_NAME ) t \n" +
                    "WHERE\n" +
                    "\tTABLE_NAME ='"+tableName+"'";
            List<String> index = jdbcTemplate.queryForList(indexSql, String.class);
            String countSql = "select count(%s) from %s";
            Integer count1 = jdbcTemplate.queryForObject(String.format(countSql, index.get(0), tableName), Integer.class);
            assert count1 != null;
            if(count1 == 1){
                return report;
            }
            String realSql = "select count(%s) from %s where %s";
            String realSql2 = String.format(realSql, index.get(0), tableName, expressionsWhere);

            Integer count2 = jdbcTemplate.queryForObject(realSql2,Integer.class);
            if(count1.equals(count2)){
                report.setDesc("请超看where字段是否失效，操作结果可能在全表上进行");
                report.setLevel(Report.Level.WARNING);
                report.setPass(false);
            }
        }
        return report;
        //return report;
    }

    @Override
    public List<SqlTypes> scope() {
        return Arrays.asList(SqlTypes.DELETE);
    }




}
