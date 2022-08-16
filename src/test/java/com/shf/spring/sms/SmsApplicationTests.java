package com.shf.spring.sms;


import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


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



    String sql = "SELECT a1, a2 FROM t1 AS a1 INNER JOIN t2 AS a2 WHERE a1.id=a2.id";
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

        Expression expr = jSqlParseAstt.getWhere();
        System.out.println(expr);
    }

}

