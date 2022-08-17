package com.shf.spring.sms.controller;

import cn.hutool.core.lang.hash.Hash;
import com.shf.spring.sms.entity.GaoliTest;
import com.shf.spring.sms.mapper.GaoliTestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sqlTest")
@Slf4j
public class SqlTestController {
    @Autowired
    GaoliTestMapper mapper;

    @GetMapping("/getPage")
    public String getPage(){
        return "taskTest/sqlTest";
    }

    @GetMapping("/deleteWhereCheck/{type}")
    @ResponseBody
    public void deleteWhereCheck(@PathVariable int type){
        //System.out.println("come");
        try{
            if(type == 0){
                mapper.deleteById(7);
            }
            if(type == 1){
                mapper.deleteWhere();
            }
            log.info("success");
        }catch (Exception e){
           log.error("false");
        }
    }

    @GetMapping("/selectCheck/{type}/{two}")
    @ResponseBody
    public void selectCheck(@PathVariable int type, @PathVariable int two){
        try{
            if(type == 0){
                if(two == 0){
                    GaoliTest temp= mapper.selectById(2);
                    if(temp == null){
                        return;
                    }
                    log.info(temp.toString());
                }else if(two == 1){
                    List<GaoliTest> temp = mapper.selectAll();
                    log.info(temp.toString());
                }
            }
            else if(type == 1){
                GaoliTest temp;
                if(two == 0){
                    temp = mapper.selectIndex1();
                }else{
                    temp = mapper.selectIndex2();
                }
                if(temp == null){
                    return;
                }
                log.info(temp.toString());
            }
            else if(type == 2){
                List<Map<String, String>> temp = mapper.selectLayer1();
            }
        }catch (Exception e){
            log.error("selectCheck, type: {}, two: {}, e: {}", type, two, e);
            e.printStackTrace();
        }
    }

}
