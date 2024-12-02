package com.example.adminservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class admin {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;


    public admin(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }



}
