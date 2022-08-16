package com.shf.spring.sms.task.PrintResult;

import java.util.List;

public interface Appender {
    void print(List<Report> reports);
}
