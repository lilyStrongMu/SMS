package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

class JoinAnalysisTest {
    JoinAnalysis joinAnalysis=new JoinAnalysis();

    @Test
    void getNewWeight() {
        TreeMap<String, List<String>> input = InputAdpter.change();
        List<String> joinSqlInform=input.get("1_0");
        Set<CheckNode> newWeight = joinAnalysis.getNewWeight(joinSqlInform);
        System.out.println(newWeight);
    }
}