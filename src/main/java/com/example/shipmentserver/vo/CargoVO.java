package com.example.shipmentserver.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CargoVO {
    @NotNull(message = "缺少名称")
    private String name;
    @NotNull(message = "缺少长度")
    @Min(0)
    private Float length;
    @NotNull(message = "缺少宽度")
    @Min(0)
    private Float width;
    @NotNull(message = "缺少高度")
    @Min(0)
    private Float height;
    @NotNull(message = "缺少重量")
    @Min(0)
    private Float weight;
}