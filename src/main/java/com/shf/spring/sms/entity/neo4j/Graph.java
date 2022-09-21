package com.shf.spring.sms.entity.neo4j;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Graph {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Vertex {
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Edge {
        private Integer source;
        private Integer target;
        private String relation;
        private Double value;
    }

    private List<Vertex> nodes;
    private List<Edge> edges;

    public Graph(Builder builder) {
        nodes = builder.nodes;
        edges = builder.edges;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<Vertex> nodes;
        private List<Edge> edges;

        private Builder() {
        }

        public Builder nodes(List<Vertex> nodes) {
            this.nodes = nodes;
            return this;
        }

        public Builder edges(List<Edge> edges) {
            this.edges = edges;
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }
    }
}
