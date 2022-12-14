package com.shf.spring.sms.Interceptor;

import com.shf.spring.sms.services.impl.SaveSqlImpl;
import com.shf.spring.sms.services.inter.SaveSql;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.Analyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAnalyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.CheckThing.check.Checker;
import com.shf.spring.sms.task.CheckThing.holder.AutoHolder;
import com.shf.spring.sms.task.CheckThing.holder.CheckerHolder;
import com.shf.spring.sms.task.CheckThing.rule.CheckRule;
import com.shf.spring.sms.task.CheckThing.rule.DeleteWhereCheck;
import com.shf.spring.sms.task.CheckThing.rule.WriteClearlySelectFieldRule;
import com.shf.spring.sms.task.PrintResult.Appender;
import com.shf.spring.sms.task.PrintResult.DefaultAppender;
import com.shf.spring.sms.task.PrintResult.Report;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.logging.jdbc.StatementLogger;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})
})
@Component
@Slf4j
public class MybatisSelectInceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result=invocation.proceed();
        //??????sql
        try{
            String sql;
            Statement statement=(Statement) invocation.getArgs()[0];
            if(Proxy.isProxyClass(statement.getClass())){
                MetaObject metaObject= SystemMetaObject.forObject(statement);
                Object h= metaObject.getValue("h");
                if(h instanceof StatementLogger){
                    RoutingStatementHandler rsh=(RoutingStatementHandler) invocation.getTarget();
                    sql=rsh.getBoundSql().getSql();
                }else {
                    PreparedStatementLogger psl=(PreparedStatementLogger) h;
                    sql=psl.getPreparedStatement().toString();
                }
            }else{
                sql=statement.toString();
            }
            //????????????,????????????????????????log
            if(!sql.contains("sys_log")){

                int index=sql.indexOf(":");
                int length=sql.length();
                String sqlString=sql.substring(index+1, length);
                if(sqlString.contains("select") || sqlString.contains("SELECT")){
                    SaveSql saveSql = new SaveSqlImpl();
                    saveSql.saveSql(sqlString, 2);
                }
                Analyzer analyzer = new JSqlParseAnalyzer();
                AST tree = analyzer.analyze(sqlString);
                AutoHolder holder = new AutoHolder();
                Appender appender = new DefaultAppender();
                StringBuilder sb = new StringBuilder();
                //????????????????????????????????????
                System.out.println("-----------start--------");
                Long b = System.currentTimeMillis();
                for (Checker checker : CheckerHolder.getCheckers().values()){
                    if(!"SELECT".equals(checker.getName())){
                        continue;
                    }
                    //??????????????????????????????
                    List<Report> reports = checker.check(tree);
                    //??????
                    appender.print(reports);
                }
                log.info(" cost " + (System.currentTimeMillis() - b) + "ms");
                System.out.println("--------------end-------------");
            }
            log.info("------------------------");
            log.info("sql ======> " + sql);
            log.info("------------------------");
        }catch (Exception e){
            log.error("??????????????????");
            e.printStackTrace();
        }
        return result;
    }
}

