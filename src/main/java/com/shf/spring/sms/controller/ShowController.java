package com.shf.spring.sms.controller;

import com.shf.spring.sms.entity.neo4j.Graph;
import com.shf.spring.sms.task2.GetDataFromNode4j;
import com.shf.spring.sms.task2.InputAdpter;
import com.shf.spring.sms.task2.SaveInGraphDataBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/show")
public class ShowController {
    SaveInGraphDataBase saveInGraphDataBase = new SaveInGraphDataBase(InputAdpter.change());

    @GetMapping("/getShowPage")
    public String getShowPage(){
        return "showTask/forceGraphDemo";
    }

    @GetMapping("/graph")
    @ResponseBody
    public Graph getGraphData() {
        GetDataFromNode4j getDataFromNode4j = new GetDataFromNode4j();
        return getDataFromNode4j.getNeo4jData();
    }

    /*
    接下来需要一个数据库来存放相应的sql语句,每次分析一个sql
     */
    @GetMapping("/analyize/sql")
    public void analyzeSql(){
        saveInGraphDataBase.saveData();
    }
}
