package com.example.shipmentserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private String phone;
    private String organization;
    private LocalDateTime registrationDate;
    private Integer role;

    public User(Integer id, String username, String password, Integer role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.avatar = "//lf1-xgcdn-tos.pstatp.com/obj/vcloud/vadmin/start.8e0e4855ee346a46ccff8ff3e24db27b.png";
    }
}