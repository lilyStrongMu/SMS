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
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.List;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
})
@Component
@Slf4j
public class MybatisUpdateInceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result=invocation.proceed();
        //获取sql
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
            //插入日志,过滤掉日志本身的log
            if(!sql.contains("sys_log")){

                int index=sql.indexOf(":");
                int length=sql.length();
                String sqlString=sql.substring(index+1,length);
                if(sqlString.contains("delete") || sqlString.contains("DELETE")){
                    SaveSql saveSql = new SaveSqlImpl();
                    saveSql.saveSql(sqlString, 4);
                }else if(sqlString.contains("update") || sqlString.contains("UPDATE")){
                    SaveSql saveSql = new SaveSqlImpl();
                    saveSql.saveSql(sqlString, 1);
                }
                Analyzer analyzer = new JSqlParseAnalyzer();
                AST tree = analyzer.analyze(sqlString);
                AutoHolder holder = new AutoHolder();
                Appender appender = new DefaultAppender();
                //遍历规则检查器，开始检查
                for (Checker checker : CheckerHolder.getCheckers().values()){
                    if("select".equals(checker.getName())){
                        continue;
                    }
                    //每个规则生成一个报告
                    List<Report> reports = checker.check(tree);
                    //输出
                    appender.print(reports);
                }

            }
            log.info("sql==================="+sql);
        }catch (Exception e){
            log.error("日志插入失败");
            e.printStackTrace();
        }
        return result;
    }
}
