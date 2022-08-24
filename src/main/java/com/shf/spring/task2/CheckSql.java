package com.shf.spring.task2;


import com.shf.spring.sms.dto.CheckNode;

import java.util.List;

public interface CheckSql {
    List<CheckNode> getNewWeight(List<String> Sql);
}
