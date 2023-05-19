package com.example.shipmentserver.vo;

import com.example.shipmentserver.model.Shipment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderShipmentVO {
    @NotEmpty(message = "订单id不能为空")
    Integer orderId;
    @NotBlank(message = "描述不能为空")
    String description;
    @NotEmpty(message = "类型不能为空")
    Shipment.Type type;
}
