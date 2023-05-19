package com.example.shipmentserver.dao;

import com.example.shipmentserver.model.ShipOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<ShipOrder, Integer> {
    List<ShipOrder> findAllByUserIdOrderByCreateTimeDesc(Integer userId);
    ShipOrder findOrderByIdAndUserId(Integer id, Integer userId);
    ShipOrder findByIdAndUserIdOrderByCreateTimeDesc(Integer id, Integer userId);
}
