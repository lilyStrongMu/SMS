package com.shf.spring.sms;

import com.shf.spring.sms.entity.neo4j.Graph;
import com.shf.spring.sms.handler.Neo4jHandler;
import com.shf.spring.sms.mapper.GaoliTestMapper;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.Analyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAnalyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.CheckThing.rule.CheckRule;
import com.shf.spring.sms.task.CheckThing.rule.NestLayerCheck_v2;
import com.shf.spring.sms.task.PrintResult.Report;
import com.shf.spring.sms.task2.GetDataFromNode4j;
import com.shf.spring.sms.task2.InputAdpter;
import com.shf.spring.sms.task2.SaveInGraphDataBase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsApplication.class)
@MapperScan("com.shf.spring.sms.mapper")
class SmsApplicationTest {
    @Autowired
    GaoliTestMapper mapper;

    SaveInGraphDataBase saveInGraphDataBase = new SaveInGraphDataBase(InputAdpter.change());

    @Test
    void main() {
        mapper.selectLayer1();
    }

    @Test
    public void testCypherHandler() {
        Neo4jHandler neo4jHandler = new Neo4jHandler();
//        neo4jHandler.insertNodeAndBiRelations("test1", "test2", 1086);
//        Optional.ofNullable(neo4jHandler.findWeightByNode("test1", "test3"))
//                .orElse(Collections.emptySet())
//                .forEach(System.out::println);
        Set<Integer> weightByNode = neo4jHandler.findWeightByNode("ttt", "a");
        System.out.println(weightByNode);
        neo4jHandler.close();
    }

    @Test
    public void testNeo4j() {
        saveInGraphDataBase.saveData();
    }

    @Test
    public void deleteAllDataInNeo4J() {
        // 查看所有的节点  `match (n) return n`
        Neo4jHandler neo4jHandler = new Neo4jHandler();
        neo4jHandler.deleteAllData();
    }

    @Test
    public void testGetNeo4jData() {
        GetDataFromNode4j getDataFromNode4j = new GetDataFromNode4j();
        Graph neo4jData = getDataFromNode4j.getNeo4jData();
        System.out.println(neo4jData);
    }
}