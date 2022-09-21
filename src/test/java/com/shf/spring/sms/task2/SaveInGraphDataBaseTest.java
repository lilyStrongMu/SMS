package com.shf.spring.sms.task2;

import com.shf.spring.sms.SmsApplication;
import com.shf.spring.sms.dto.CheckNode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsApplication.class)
@MapperScan("com.shf.spring.sms.mapper")
class SaveInGraphDataBaseTest {

    @Test
    void saveData() {
//        SaveInGraphDataBase saveInGraphDataBase = new SaveInGraphDataBase();
//        CheckNode checkNode = new CheckNode("a", "b", 1);
//        CheckNode checkNode1 = new CheckNode("b", "c", 2);
//        CheckNode checkNode2 = new CheckNode("a", "b", 100);
//        List<CheckNode> checkNodes = new ArrayList<>(Arrays.asList(checkNode, checkNode1, checkNode2));
//        saveInGraphDataBase.saveData(checkNodes);
    }
}