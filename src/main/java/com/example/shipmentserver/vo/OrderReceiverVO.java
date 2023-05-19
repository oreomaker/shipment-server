package com.example.shipmentserver.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderReceiverVO {
    @NotEmpty(message = "收件人姓名不能为空")
    private String name;
    @NotEmpty(message = "收件人地址不能为空")
    private String address;
    @NotEmpty(message = "收件人电话不能为空")
    private String phone;
}
