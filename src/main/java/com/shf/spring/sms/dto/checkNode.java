package com.shf.spring.sms.dto;

import lombok.Data;

@Data
public class checkNode {
    String name;
    int weight;
    public checkNode(){

    }
    public checkNode(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
}
