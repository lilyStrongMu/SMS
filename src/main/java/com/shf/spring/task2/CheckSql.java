package com.shf.spring.task2;

import com.shf.spring.sms.dto.checkNode;

import java.sql.Wrapper;
import java.util.List;

public interface CheckSql {
    List<checkNode> getNewWeight(List<String> Sql);
}
