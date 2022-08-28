package com.shf.spring.sms.controller;

import com.shf.spring.sms.task2.InputAdpter;
import com.shf.spring.sms.task2.SaveInGraphDataBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/show")
public class ShowController {
    SaveInGraphDataBase saveInGraphDataBase = new SaveInGraphDataBase(InputAdpter.change());


}
