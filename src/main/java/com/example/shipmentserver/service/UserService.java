package com.example.shipmentserver.service;

import com.example.shipmentserver.component.BaseResponse;
import com.example.shipmentserver.component.UserInfo;
import com.example.shipmentserver.dao.UserRepository;
import com.example.shipmentserver.model.User;
import com.example.shipmentserver.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    enum Role { // 当前仅用作提示，未实际使用
        ADMIN,
        USER
    }

    public BaseResponse login(String username, String password) {
        //md5加密处理
        String plainPassword = password;
        String ciphertext = DigestUtils.md5DigestAsHex(plainPassword.getBytes());

        User user = userRepository.findUserByUsernameAndPassword(username, ciphertext);

        //没查到返回登录失败
        if(user == null) {
            return BaseResponse.error("登录失败");
        }

        // token生成
        var map = new LinkedHashMap<String, String>();
        map.put("id", String.valueOf(user.getId()));
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        if(user.getRole() == 0){
            map.put("role", "admin");
        }
        else{
            map.put("role", "user");
        }
        String token = JwtToken.create(map);

        map.put("token", token);
        return BaseResponse.success(map);
    }

    public BaseResponse<Map<String, String>> register(String username, String password) {
        //md5加密储存
        String plainPassword = password;
        String ciphertext = DigestUtils.md5DigestAsHex(plainPassword.getBytes());

        User user = new User(null, username, ciphertext, 0);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return BaseResponse.error("该账号已被注册");
        }

        var res = new HashMap<String, String>();
        // token生成
        var map = new LinkedHashMap<String, String>();
        map.put("status", "ok");
        return BaseResponse.success(res);
    }

    public BaseResponse<Map<String, String>> changePassword(String username, String password) {

        String plainPassword = password;
        String ciphertext = DigestUtils.md5DigestAsHex(plainPassword.getBytes());

        userRepository.updateUserByUsername(username, ciphertext);

        return BaseResponse.success(null);
    }

    public User findUserById(Integer i) {
        return userRepository.findById(i).get();
    }

    public BaseResponse info(String token) {
        JwtToken.verify(token);
        var map = UserInfo.getLocalMap();
        if (map == null) {
            return BaseResponse.error("token无效");
        }
        String username = map.get("username");
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return BaseResponse.error("用户不存在");
        }
        var res = new LinkedHashMap<String, String>();
        res.put("id", String.valueOf(user.getId()));
        res.put("username", user.getUsername());
        res.put("email", user.getEmail());
        if(user.getRole() == 0){
            res.put("role", "admin");
        }
        else{
            res.put("role", "user");
        }
        res.put("avatar", user.getAvatar());
        res.put("organization", user.getOrganization());
        res.put("phone", user.getPhone());
        res.put("registrationDate", String.valueOf(user.getRegistrationDate()));
        return BaseResponse.success(res);
    }

    public BaseResponse logout() {
        // TODO: logout
        return BaseResponse.success("登出成功");
    }

    public BaseResponse getAllUsers() {
        if(!Objects.equals(UserInfo.get("role"), "admin")){
            return BaseResponse.error("无权限");
        }
        return BaseResponse.success(userRepository.findAll());
    }

    public BaseResponse updateUser(Integer id, User user) {
        if(!UserInfo.get("role").equals("admin")){
            return BaseResponse.error("权限不足");
        }
        try {
            user = userRepository.save(user);
        }
        catch(Exception e) {
            return BaseResponse.error("更新失败");
        }
        return BaseResponse.success(user);
    }

    public BaseResponse deleteUser(Integer id) {
        if(!UserInfo.get("role").equals("admin")){
            return BaseResponse.error("权限不足");
        }
        userRepository.deleteById(id);
        return BaseResponse.success(null);
    }
}
