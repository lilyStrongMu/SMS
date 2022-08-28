package com.shf.spring.sms.entity.neo4j;

import lombok.Data;
import java.util.List;

@Data
public class Graph {

    @Data
    class Vertex {
        private String name;
    }

    @Data
    class Edge {
        private Integer source;
        private Integer target;
        private String relation;
        private Integer value;
    }

    private List<Vertex> nodes;
    private List<Edge> edges;

}
