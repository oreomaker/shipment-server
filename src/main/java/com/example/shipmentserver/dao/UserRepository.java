package com.example.shipmentserver.dao;

import com.example.shipmentserver.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findUserByUsernameAndPassword(String username, String password);
    public User findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.password = ?2 where u.username = ?1")
    public int updateUserByUsername(String username, String password);

    User findUserById(Integer id);
}
