package com.vladmeh.javaRushTestTask.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeAction {
    @RequestMapping("/")
    public String homeAction() {
        return "index";
    }
}
