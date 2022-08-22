package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;

import java.util.List;
import java.util.Set;

public interface CheckSql {
    Set<CheckNode> getNewWeight(List<String> Sql);
}
