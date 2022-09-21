package com.shf.spring.sms.task.CheckThing.rule;

import com.shf.spring.sms.SmsApplication;
import com.shf.spring.sms.mapper.GaoliTestMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsApplication.class)
@MapperScan("com.shf.spring.sms.mapper")
class DeleteWhereCheckTest {
    @Autowired
    GaoliTestMapper mapper;

    @Test
    void match() {
        /*
        deleteWhere的sql语句：delete from gaoli_test where id > 1，
        测试库中的所有id都比1大，所以造成了id失效
         */
        mapper.deleteWhere();
    }
}