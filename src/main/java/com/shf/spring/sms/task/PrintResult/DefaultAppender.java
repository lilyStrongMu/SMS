package com.shf.spring.sms.task.PrintResult;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DefaultAppender implements Appender {
    @Override
    public void print(List<Report> result) {
        if (result == null || result.size() < 1){
            return;
        }
        System.out.println("========报告如下=========");

        for (Report report : result){
            //不通过才打印
            if (!report.isPass()){
                log.error(" 违反自定义规则:"+report.desc);
                System.out.println();
            }
        }
    }
}

