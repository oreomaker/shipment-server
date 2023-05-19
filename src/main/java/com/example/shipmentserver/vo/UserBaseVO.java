package com.example.shipmentserver.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBaseVO {
    @NotBlank(message = "名字为必填项")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
