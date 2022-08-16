package com.shf.spring.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.shf.spring.sms.entity.GaoliTest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GaoliTestMapper extends BaseMapper<GaoliTest> {

    List<GaoliTest> selectAll();

    GaoliTest selectIndex1();

    GaoliTest selectIndex2();

    List<Map<String, String>> selectLayer1();

    void deleteWhere();
}
