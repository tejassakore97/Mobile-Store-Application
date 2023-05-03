package com.app.MobileAppProject.reposistory;

import com.app.MobileAppProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserReposistory extends JpaRepository<User,Integer> {
    ;
}
