package com.app.MobileAppProject.controller;

import com.app.MobileAppProject.dto.ProductDTO;
import com.app.MobileAppProject.dto.UserDTO;
import com.app.MobileAppProject.model.Product;
import com.app.MobileAppProject.model.Role;
import com.app.MobileAppProject.model.User;
import com.app.MobileAppProject.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("register")
public class UserRegisterController {
    @Autowired
    UserService userService;

    public UserRegisterController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping
    private  String registerGet(Model model)
    {
        model.addAttribute("user",new UserDTO());
        return "register";
    }

    @PostMapping
    public  String RegisterUserAccount(@ModelAttribute("user")UserDTO userDTO){
        User user=new User();
        user.setId(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        userService.add(user);
       // return "login";
       return "redirect:/register?success";
    }

}
