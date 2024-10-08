package com.application.courselibrary.controller.practise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class working {

    @GetMapping("/home")
    public String home(){
        return "/operations/home";
    }

}
