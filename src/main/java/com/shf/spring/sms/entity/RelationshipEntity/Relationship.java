package com.shf.spring.sms.entity.RelationshipEntity;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@RelationshipEntity(type = "relationship")
public class Relationship {
    @Id
    @GeneratedValue
    private long id;

    @StartNode
    private String parent;

    @EndNode
    private String child;

    private int weight;
}
