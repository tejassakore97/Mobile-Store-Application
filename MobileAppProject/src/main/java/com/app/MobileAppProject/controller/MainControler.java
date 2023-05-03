package com.app.MobileAppProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControler {
    @GetMapping("/login")
    public  String login()
    {
//        if(admin.getPassword().equals(admindata.get().getPassword())) {
//
//            return "adminHome";
//        }
//
//        else {
//
//            //return "errorpassword";
//            return  "adminHome";
//
//        }
        return "login";
    }
}
