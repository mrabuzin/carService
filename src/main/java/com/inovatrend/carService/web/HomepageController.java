package com.inovatrend.carService.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomepageController {

    @RequestMapping("/")
    public String showHomePage(){
        return "index";
    }

}
