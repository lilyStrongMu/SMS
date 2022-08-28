package com.shf.spring.sms.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class CheckNode implements Comparable<CheckNode>{
    String name1;
    String name2;
    int weight;
    public CheckNode(){

    }
    public CheckNode(String name1, String name2, int weight){
        this.name1 = name1;
        this.name2 = name2;
        this.weight = weight;
    }

    public CheckNode(Builder builder) {
        this.name1 = builder.name1;
        this.name2 = builder.name2;
        this.weight = builder.weight;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name1;
        private String name2;
        private int weight;

        public Builder name1(String val) {
            name1 = val;
            return this;
        }

        public Builder name2(String val) {
            name2 = val;
            return this;
        }

        public Builder weight(int val) {
            weight = val;
            return this;
        }

        public CheckNode build() {
            return new CheckNode(this);
        }
    }

    @Override
    public int compareTo(CheckNode o) {
        if(!o.name1.equals(this.name1)){
            return this.name1.compareTo(o.name1);
        }else{
            return this.name2.compareTo(o.name2);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(name1, name2) + Objects.hash(name2, name1);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass())return false;
        CheckNode node = (CheckNode) o;
        return Objects.equals(name1, node.name1) && Objects.equals(name2, node.name2) ||
                Objects.equals(name1, node.name2) && Objects.equals(name2, node.name1);
    }
}
