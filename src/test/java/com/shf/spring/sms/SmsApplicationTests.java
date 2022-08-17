package com.shf.spring.sms;


import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.ASTNode.SqlTypes;
import com.shf.spring.sms.task.PrintResult.DefaultAppender;
import com.shf.spring.sms.task.PrintResult.Report;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
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
import java.util.Map;


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



    String sql = "SELECT ABCD  FROM CUSTOMERS WHERE SALARY > (SELECT SALARY FROM CUSTOMERS WHERE NAME='Komal') AND SALARY < 10000;";
//    String sql = "select a1.x, a2.y from t1 as a1 inner join t2 as a2 where a1.id=a2.id;";
//    String sql = "select person_id_card, brand, license from car where person_id_card = ID000001 union select person_id_card, brand, license from car where person_id_card = ID000002;";
//    String sql = "UPDATE T_Person SET FAge = 12 WHERE FNAME=Tom";
//    String sql = "SELECT DISTINCT Country FROM Customers;";
//    String sql = "DELETE FROM T_Person WHERE FAge > 20 or FRemark = 'mars';";
//    String sql = "SELECT Company, OrderNumber FROM Orders GROUP BY Company";
//    String sql = "SELECT Customer,OrderDate,SUM(OrderPrice) FROM Orders GROUP BY Customer,OrderDate";
//    String sql = "SELECT Company, OrderNumber FROM Orders ORDER BY Company";
//    String sql = "select distinct score,name from (select id, age, score, name from student where age > 5 group by sex) t, (select id , DISTINCT arange from player group by sex) x where t.id = xr.id";
//    String sql = "select * from (SELECT name,SUM(salary) FROM student GROUP BY name,grad) t0,(SELECT name,SUM(salary) FROM teacher GROUP BY name,grad) t1";


    Statement statement;
    {
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private JSqlParseAst jSqlParseAst= new JSqlParseAst(statement, sql);

    @Test
    public void test_JSqlParserAst() {


//        Expression expression = jSqlParseAst.getWhere();
//        System.out.println(expression);

//        List<SelectItem> tables = jSqlParseAst.getSelects();
//        System.out.println(tables);
//
        Select select = (Select) statement;
        SelectBody selectBody = select.getSelectBody();
        Map map = jSqlParseAst.test_select_subselect(selectBody);
        System.out.println(map);

    }



    @Autowired
    private DefaultAppender defaultAppender= new DefaultAppender();

    @Test
    public void test_print_result(){
        List<Report> result = new ArrayList<Report>();
        Report report = new Report(false,"请写明查询字段，不要使用select *","select * from test", Report.Level.ERROR,"null");
        result.add(report);
        defaultAppender.print(result);

    }

}

