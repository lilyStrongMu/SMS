package com.shf.spring.sms.Interceptor;

import com.shf.spring.sms.services.impl.SaveSqlImpl;
import com.shf.spring.sms.services.inter.SaveSql;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.CheckThing.rule.CheckRule;
import com.shf.spring.sms.task.CheckThing.rule.DeleteWhereCheck;
import com.shf.spring.sms.task.CheckThing.rule.WriteClearlySelectFieldRule;
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

@Intercepts({
        @Signature(type = StatementHandler.class, method = "select", args = {Statement.class})
})
@Component
@Slf4j
public class MybatisSelectInceptor implements Interceptor {

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
                String sqlString=sql.substring(index+1, length);
                if(sqlString.contains("select") || sqlString.contains("SELECT")){
                    SaveSql saveSql = new SaveSqlImpl();
                    saveSql.saveSql(sqlString, 4);
                }
                net.sf.jsqlparser.statement.Statement statement2 = CCJSqlParserUtil.parse(sqlString);
                AST astNode = new JSqlParseAst(statement2,sql);
                CheckRule checkRule = new WriteClearlySelectFieldRule();
                Report report= checkRule.match(astNode);
                if(!report.isPass()){
                    System.out.println("wrong");
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

