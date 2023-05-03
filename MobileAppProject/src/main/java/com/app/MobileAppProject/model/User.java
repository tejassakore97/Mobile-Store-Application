package com.app.MobileAppProject.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users" ,uniqueConstraints=@UniqueConstraint(columnNames ="email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ROLL_ID", referencedColumnName = "id")}
    )
    private List<Role> roles;

    public User(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User() {

    }

    public <T> User(String firstname, String lastname, String email, String password, List<T> roleUser) {
    }
}

