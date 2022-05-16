package com.example.mojingalgorithm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller
public class PageController {
    @GetMapping(path="/")
    public String index(){
        return "index";
    }
}
