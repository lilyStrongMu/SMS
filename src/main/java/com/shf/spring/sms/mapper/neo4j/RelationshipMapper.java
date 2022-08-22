package com.shf.spring.sms.mapper.neo4j;


import org.neo4j.ogm.annotation.RelationshipEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipMapper extends Neo4jRepository<RelationshipEntity, Long> {
}
