package com.example.shipmentserver.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderSenderVO {
    @NotEmpty(message = "寄件人姓名不能为空")
    private String name;
    @NotEmpty(message = "寄件人地址不能为空")
    private String address;
    @NotEmpty(message = "寄件人电话不能为空")
    private String phone;
}
