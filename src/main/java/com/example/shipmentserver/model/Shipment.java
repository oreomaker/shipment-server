package com.example.shipmentserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDateTime time;
    private String description;
    private Type type;
    @ManyToOne
    @JoinColumn(name = "shipOrder_id")
    @JsonBackReference
    private ShipOrder shipOrder;

    public enum Type {
        ORDER, // 下单
        PROCESSING, // 揽收
        IN_TRANSIT, // 运输中
        DELIVERED // 已送达
    }
}
