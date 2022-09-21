package com.shf.spring.sms.task2;

import com.shf.spring.sms.dto.CheckNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LayerTest {
    Layer layer = new Layer(InputAdpter.change());
    @Test
    void getLast() {
        List<CheckNode> list = layer.getLast();
        System.out.println(list);
    }
}