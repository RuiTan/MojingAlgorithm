package com.example.mojingalgorithm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@CrossOrigin
@Controller
public class PageController {
    @GetMapping(path="/")
    public String index(){
        return "index";
    }

    @GetMapping(path="/index")
    public String index2(){
        return "index";
    }

    @GetMapping(path="/collocation")
    public String collocation() {
        return "collocation";
    }
}
