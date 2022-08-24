package com.shf.spring.sms;

import com.shf.spring.sms.handler.Neo4jHandler;
import com.shf.spring.sms.mapper.GaoliTestMapper;
import com.shf.spring.sms.task.ASTNode.AST;
import com.shf.spring.sms.task.ASTNode.Analyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAnalyzer;
import com.shf.spring.sms.task.ASTNode.JSqlParseAst;
import com.shf.spring.sms.task.CheckThing.rule.CheckRule;
import com.shf.spring.sms.task.CheckThing.rule.NestLayerCheck_v2;
import com.shf.spring.sms.task.PrintResult.Report;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsApplication.class)
@MapperScan("com.shf.spring.sms.mapper")
class SmsApplicationTest {
    @Autowired
    GaoliTestMapper mapper;

    @Test
    void main() {
        mapper.selectLayer1();
    }

    @Test
    public void testCypherHandler() {
        Neo4jHandler neo4jHandler = new Neo4jHandler();
        neo4jHandler.insertNodeAndBiRelations("test1", "test2", 1086);
        Optional.ofNullable(neo4jHandler.findWeightByNode("test1", "test3"))
                .orElse(Collections.emptySet())
                .forEach(System.out::println);
    }

    @Test
    public void deleteAllDataInNeo4J() {
        // 查看所有的节点  `match (n) return n`
        Neo4jHandler neo4jHandler = new Neo4jHandler();
        neo4jHandler.deleteAllData();
    }
}