package com.example.shipmentserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ship_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipOrder {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private String fromAddress;
    private String toAddress;
    private String senderName;
    private String receiverName;
    private String senderPhone;
    private String receiverPhone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
    private LocalDateTime createTime = LocalDateTime.now();
    private Status orderStatus = Status.PENDING;
    @OneToMany(mappedBy = "shipOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Shipment> shipments;

    public enum Status {
        PENDING, // 待发货
        DELIVERING, // 运输中
        DELIVERED // 已送达
    }
}
