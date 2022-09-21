package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import com.shf.spring.sms.entity.neo4j.Graph;
import com.shf.spring.sms.handler.Neo4jHandler;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;

import java.util.*;

public class GetDataFromNode4j {

    public Graph getNeo4jData() {
        List<Graph.Vertex> nodes = new ArrayList<>();
        List<Graph.Edge> edges = new ArrayList<>();
        Neo4jHandler neo4jHandler = new Neo4jHandler();
        String cypher = "match (n)-[r]->(m) return startNode(r).name as sid, endNode(r).name " +
                "as eid, r.weight as rel";
        Result result = neo4jHandler.runCypher(cypher);
        Set<CheckNode> set = new HashSet<>();
        Map<String, Integer> nodeNameById = new HashMap<>();
        // 节点编号
        int idx = 0;
        while (result.hasNext()) {
            Record record = result.next();
            String startNodeName = record.get("sid").asString();
            String endNodeName = record.get("eid").asString();
            Integer weight = record.get("rel").asInt();
            CheckNode checkNode = CheckNode.builder()
                    .name1(startNodeName)
                    .name2(endNodeName)
                    .build();
            // set用于判断是否是重边，根据业务场景不应该有重边
            if (!set.contains(checkNode)) {
                set.add(checkNode);
                // 根据节点名判断是否已经存在此节点了
                if (!nodeNameById.containsKey(startNodeName)) {
                    Graph.Vertex startNode = new Graph.Vertex(startNodeName);
                    nodeNameById.put(startNodeName, idx);
                    idx++;
                    nodes.add(startNode);
                }
                if (!nodeNameById.containsKey(endNodeName)) {
                    Graph.Vertex endNode = new Graph.Vertex(endNodeName);
                    nodeNameById.put(endNodeName, idx);
                    idx++;
                    nodes.add(endNode);
                }
                int source = nodeNameById.get(startNodeName);
                int target = nodeNameById.get(endNodeName);
                Graph.Edge edge = new Graph.Edge(source, target, String.valueOf(weight), weight / 100d + 2);
                edges.add(edge);
            }
        }
        neo4jHandler.close();
        return Graph.builder()
                .nodes(nodes)
                .edges(edges)
                .build();
    }
}
