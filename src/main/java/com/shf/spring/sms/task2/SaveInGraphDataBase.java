package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import com.shf.spring.sms.handler.Neo4jHandler;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class SaveInGraphDataBase {
    Layer layerAnalyzer;
    public SaveInGraphDataBase(TreeMap<String, List<String>> map){
        this.layerAnalyzer = new Layer(map);
    }

    public void saveData(){
        List<CheckNode> list = layerAnalyzer.getLast();
        Neo4jHandler neo4jHandler = new Neo4jHandler();
        list.forEach(checkNode -> {
            Set<Integer> weightByNode = neo4jHandler.findWeightByNode(checkNode.getName1(), checkNode.getName2());
            if (!CollectionUtils.isEmpty(weightByNode)) {
                Integer weight = weightByNode.stream().findFirst().get();
                if (checkNode.getWeight() > weight) {
                    neo4jHandler.updateWeightByNode(checkNode.getWeight(), checkNode.getName1(), checkNode.getName2());
                }
            } else {
                neo4jHandler.insertNodeAndBiRelations(checkNode.getName1(), checkNode.getName2(), checkNode.getWeight());
            }
        });
        neo4jHandler.close();

    }
}
