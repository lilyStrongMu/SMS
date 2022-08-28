package com.shf.spring.sms.task2;

import com.shf.spring.sms.SmsApplication;
import com.shf.spring.sms.dto.CheckNode;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsApplication.class)
@MapperScan("com.shf.spring.sms.mapper")
class RelationshipTest {
    Relationship re = new Relationship();
    InCheck in = new InCheck();
    JoinAnalysis joinAnalysis = new JoinAnalysis();

    @Test
    void getNewWeight() {
        TreeMap<String, List<String>> map = InputAdpter.change();
        Set<CheckNode> set = re.getNewWeight(map.get("1_1"));
        System.out.println(set);
    }

    @Test
    void getCheckNode() {
        Set<CheckNode> set = new HashSet<>();
        set.add(new CheckNode("Ata", "b", 95));
        CheckNode t = new CheckNode("b", "Ata", 100);
        assertTrue(set.contains(t));
    }

    @Test
    void getInWeight(){
        TreeMap<String, List<String>> map = InputAdpter.change();
        Set<CheckNode> set = in.getNewWeight(map.get("3_0"));
        System.out.println(set);
    }

    @Test
    void getJoinWeight(){
        TreeMap<String, List<String>> map = InputAdpter.change();
        Set<CheckNode> set = joinAnalysis.getNewWeight(map.get("1_0"));
        System.out.println(set);
    }

}