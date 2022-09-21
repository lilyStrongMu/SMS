package com.shf.spring.sms.task.CheckThing.rule;

import com.shf.spring.sms.SmsApplication;
import com.shf.spring.sms.dto.CheckNode;
import com.shf.spring.sms.mapper.GaoliTestMapper;
import com.shf.spring.sms.task2.InCheck;
import com.shf.spring.sms.task2.InputAdpter;
import com.shf.spring.sms.task2.JoinAnalysis;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsApplication.class)
@MapperScan("com.shf.spring.sms.mapper")
class SelectIndexCheckTest {
    @Autowired
    GaoliTestMapper mapper;

    InCheck inCheck = new InCheck();
    JoinAnalysis joinAnalysis = new JoinAnalysis();
    /*
    索引建立在name,sex上
    selectIndex2:select name, sex from gaoli_test where id = 7 order by name
    selectIndex1:select name, sex from gaoli_test where id = 7 order by name,sex
     */
    @Test
    void match() {
        mapper.selectIndex2();
        mapper.selectIndex1();
    }

    @Test
    void getNewWeight(){
        Set<CheckNode> set = joinAnalysis.getNewWeight(InputAdpter.change().get("1_0"));
        System.out.println(set);
    }

}