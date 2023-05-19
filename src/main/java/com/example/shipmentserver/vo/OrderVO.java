package com.example.shipmentserver.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderVO {
    @NotBlank(message = "缺少发方信息")
    OrderSenderVO sender;
    @NotBlank(message = "缺少收方信息")
    OrderReceiverVO receiver;
    @NotBlank(message = "缺少货物信息")
    CargoVO cargo;
}
