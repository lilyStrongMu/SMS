package com.shf.spring.sms.task.PrintResult;

/**
 * 检查报告
 */

public class Report {
    private boolean pass;   //通过标识
    private String desc;    //错误提示
    public String sql;     //sql语句
    private Level level;    //报告等级
    private String sample;  //正例，每个报告在输出时，除了报告错误外，还需展示正例，告诉用户正确的写法是什么

    public Report(String sql) {
        this.sql = sql;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isPass() {
        return pass;
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

