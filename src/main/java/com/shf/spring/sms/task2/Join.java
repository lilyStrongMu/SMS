package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Join implements CheckSql{
    @Override
    public Set<CheckNode> getNewWeight(List<String> Sql) {
        return Collections.emptySet();
    }
}
