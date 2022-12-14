package com.shf.spring.sms.task.CheckThing.rule;

import cn.hutool.extra.spring.SpringUtil;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;


public class SelectIndexCheck implements CheckRule{
    private final JdbcTemplate jdbcTemplate ;
    public SelectIndexCheck(){
        jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
    }
    @Override
    public Report match(AST tree) {
        Report report = new Report();
        report.setPass(true);
        report.setSql(tree.getSql());
        Distinct distinctColumn = tree.getDistinct();
        GroupByElement groupByElement = tree.getGroupBy();
        List<OrderByElement> orderByElement = tree.getOrderByElement();
        List<String> names = tree.getTable();
        if(distinctColumn == null && groupByElement == null && orderByElement == null ){
            return report;
        }
        List<String[]> indexs = new ArrayList<>();
        String sqlIndex = "SELECT\n" +
                "\tINDEX_COLUMN \n" +
                "FROM\n" +
                "\t( SELECT TABLE_NAME, INDEX_NAME, GROUP_CONCAT( COLUMN_NAME ) AS INDEX_COLUMN FROM information_schema.statistics WHERE table_schema = 'sms' GROUP BY TABLE_NAME, INDEX_NAME ) t \n" +
                "WHERE\n" +
                "\tTABLE_NAME = '%s'";
        for(String name : names){
            String curSql = String.format(sqlIndex, name);
            List<String> curResult = jdbcTemplate.queryForList(curSql, String.class);
            if(curResult.size() != 0){
                for(String result : curResult){
                    String[] t = result.trim().split(",");
                    indexs.add(t);
                }
            }
        }
        boolean flagF1 = true;
        boolean flagF2 = true;
        for (String[] index : indexs) {
            boolean flag1 = true, flag2 = true, flag3 = true;
            if (groupByElement != null && flagF1) {
                for (String s : index) {
                    if (!groupByElement.toString().contains(s)) {
                        flag1 = false;
                    }
                }
                if (flag1) {
                    flagF1 = false;
                }
            }
            //????????????
            if (orderByElement != null && flagF2) {
                for (String s : index) {
                    if (!orderByElement.toString().contains(s)) {
                        flag2 = false;
                    }
                }
                if (flag2) {
                    flagF2 = false;
                }
            }
            if (!flagF1 && !flagF2) {
                break;
            }
        }

        if(orderByElement != null){
            if(!flagF2){
                if(groupByElement != null){
                    if(flagF1){
                        report.setDesc("groupBy?????????????????????");
                        report.setLevel(Report.Level.ERROR);
                        report.setPass(false);
                    }
                }
            }else{
                if(groupByElement != null){
                    if(flagF1){
                        report.setDesc("groupBy?????????orderBy?????????????????????");
                    }else{
                        report.setDesc("orderBy?????????????????????");
                    }
                }else {
                    report.setDesc("orderBy?????????????????????");
                }
                report.setLevel(Report.Level.ERROR);
                report.setPass(false);
            }
        }

        if(groupByElement != null){
            if(!flagF1){
                if(orderByElement != null){
                    if(flagF2){
                        report.setDesc("orderBy?????????????????????");
                        report.setLevel(Report.Level.ERROR);
                        report.setPass(false);
                    }
                }
            }else{
                if(orderByElement != null){
                    if(flagF2){
                        report.setDesc("groupBy?????????orderBy?????????????????????");
                    }else{
                        report.setDesc("groupBy?????????????????????");
                    }
                }else {
                    report.setDesc("groupBy?????????????????????");
                }
                report.setLevel(Report.Level.ERROR);
                report.setPass(false);
            }
        }
        return report;
    }

    @Override
    public List<SqlTypes> scope() {
        return Arrays.asList(SqlTypes.SELECT);
    }

    private boolean isHere(List<Set<String>> indexs, List<String> items){

        return true;
    }
}
