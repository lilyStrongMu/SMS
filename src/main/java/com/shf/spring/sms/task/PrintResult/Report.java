package com.shf.spring.sms.task.PrintResult;

import net.sf.jsqlparser.statement.Statement;

public class Report {
    public boolean pass; //通过标识
    public String desc; //错误提示
    public String sql;//sql语句
    public Level level;//报告等级
    public String sample;//正例，每个报告在输出时，除了报告错误外，还需展示正例，告诉用户正确的写法是什么

    public boolean isPass() {
        if(pass){
            return true;
        }
        else return false;
    }
    public Report(boolean pass,String desc, String sql,Level level,String sample) {
        this.pass = pass;
        this.desc = desc;
        this.level = level;
        this.sample = sample;
        this.sql = sql;
    }

    public enum Level{
        WARNING("wanring"),
        ERROR("error"),
        INFO("info");

        private String value;
        Level(String value){
            this.value = value;
        }

    }
}
