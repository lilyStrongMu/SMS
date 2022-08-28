package com.shf.spring.sms.handler;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;

import java.util.*;

@Slf4j
public class Neo4jHandler {

    private Driver driver;

    private Session session;

    private final ThreadLocal<List<String>> threadLocal = ThreadLocal.withInitial(ArrayList::new);

    public Neo4jHandler() {
        init();
    }

    private void init() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4j2"));
        session = driver.session();
    }

    public Result runCypher(String cypher) {
        return session.run(cypher);
    }

    public Neo4jHandler createTableNode(String name) {
        String cypher = "merge (n:tableNode {name: '" + name + "'});";
        List<String> cyphers = threadLocal.get();
        cyphers.add(cypher);
        threadLocal.set(cyphers);
        return this;
    }

    public Neo4jHandler createRelation(String nodeName1, String nodeName2, Integer weight) {
        String cypher = "match (a:tableNode), (b:tableNode) where a.name='"
                + nodeName1 + "' and b.name='"
                + nodeName2 + "' merge (a) -[r:weight {weight:"
                + weight + "}]->(b);";
        List<String> cyphers = threadLocal.get();
        cyphers.add(cypher);
        threadLocal.set(cyphers);
        return this;
    }

    public Set<Integer> findWeightByNode(String nodeName1, String nodeName2) {
        String cypher = "match (a:tableNode)-[r:weight]->(b:tableNode) where a.name='"
                + nodeName1 + "' and b.name='" + nodeName2 + "' return r.weight as weight";
        Set<Integer> set = new HashSet<>();

        Result result = session.run(cypher);
        while (result.hasNext()) {
            Record record = result.next();
            set.add(record.get("weight").asInt());
        }
        return set;

    }

    public void updateWeightByNode(Integer weight, String nodeName1, String nodeName2) {
        if (Objects.isNull(weight) || Objects.isNull(nodeName1) || Objects.isNull(nodeName2)) {
            throw new RuntimeException("条件或值不能为null");
        }
        String cypher = "match (p:tableNode{name:'"
                + nodeName1 + "'})-[r]->(q:tableNode{name:'"
                + nodeName2 + "'}) set r.weight=" + weight + " return r;";
        session.run(cypher);
        cypher = "match (p:tableNode{name:'"
                + nodeName2 + "'})-[r]->(q:tableNode{name:'"
                + nodeName1 + "'}) set r.weight=" + weight + " return r;";
        session.run(cypher);
    }

    public void createHandlerExecute() {

        List<String> cyphers = threadLocal.get();
        cyphers.forEach((cypher) -> session.run(cypher));
        threadLocal.remove();
    }

    public void deleteAllData() {
        log.info("clear all data in neo4j");
        try {
            session.run("match (n) detach delete (n)");
        } finally {
            close();
        }

    }

    public void insertNodeAndBiRelations(String nodeName1, String nodeName2, Integer weight) {
        this.createTableNode(nodeName1)
                .createTableNode(nodeName2)
                .createRelation(nodeName1, nodeName2, weight)
                .createRelation(nodeName2, nodeName1, weight)
                .createHandlerExecute();
    }


    public void close() {
        driver.close();
        session.close();
    }


}
