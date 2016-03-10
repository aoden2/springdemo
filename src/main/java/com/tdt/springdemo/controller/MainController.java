package com.tdt.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping(value = "/aaa")
    public String aaa() {

        return "aaa";
    }

    @RequestMapping(value = "/login")
    public String login() {

        return "login";
    }

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
