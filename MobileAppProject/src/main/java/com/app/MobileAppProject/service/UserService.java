package com.app.MobileAppProject.service;

import com.app.MobileAppProject.dto.UserDTO;
import com.app.MobileAppProject.model.Role;
import com.app.MobileAppProject.model.User;
import com.app.MobileAppProject.reposistory.UserReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    UserReposistory userReposistory;

    public void  add(User user)
    {
        userReposistory.save(user);
    }

}
