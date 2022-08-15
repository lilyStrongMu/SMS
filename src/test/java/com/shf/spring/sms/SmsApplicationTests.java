package com.shf.spring.sms;


import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.DefaultAppender;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


//@SpringBootTest
//@ContextConfiguration(classes = SmsApplication.class)

public class SmsApplicationTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void contextLoads() {
        System.out.println(applicationContext);
    }



    String sql = "select * from(select E from (select D from (select * from (select C from (select B from A))))) as a where name = 'sql' " ;
    //SELECT * FROM A,(SELECT * FROM B) B, (SELECT * FROM C) C
    Statement statement;
    {
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private JSqlParseAst jSqlParseAstt= new JSqlParseAst(statement, sql);

    @Test
    public void testJsqlparser() {
        String s = jSqlParseAstt.getSql();
        SqlTypes sqlTypes = jSqlParseAstt.getSqlType();
        int n = jSqlParseAstt.getNestedLayers();
        //List<Column> co = jSqlParseAstt.getColumns();
        //Limit li = jSqlParseAstt.getLimit();
        //System.out.println(co);
        System.out.println(n);
    }






    @Autowired
    private DefaultAppender defaultAppender= new DefaultAppender();

    @Test
    public void testprintresult(){
        List<Report> result = new ArrayList<Report>();
        Report report = new Report(false,"请写明查询字段，不要使用select *","select * from test", Report.Level.ERROR,"null");
        result.add(report);
        defaultAppender.print(result);

    }



}

