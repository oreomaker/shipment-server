package com.example.shipmentserver.service;

import com.example.shipmentserver.component.BaseResponse;
import com.example.shipmentserver.dao.CargoRepository;
import com.example.shipmentserver.dao.OrderRepository;
import com.example.shipmentserver.dao.ShipmentRepository;
import com.example.shipmentserver.model.Cargo;
import com.example.shipmentserver.model.ShipOrder;
import com.example.shipmentserver.model.Shipment;
import com.example.shipmentserver.vo.CargoVO;
import com.example.shipmentserver.vo.OrderReceiverVO;
import com.example.shipmentserver.vo.OrderSenderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CargoRepository cargoRepository;

    public BaseResponse createOrder(Integer userId, OrderSenderVO orderSenderVO, OrderReceiverVO orderReceiverVO, CargoVO cargoVO) {
        var cargo = new Cargo();
        cargo.setData(cargoVO.getName(), cargoVO.getLength(), cargoVO.getWidth(), cargoVO.getHeight(), cargoVO.getWeight());
        cargo = cargoRepository.save(cargo);
        // sender and receiver info
        var order = new ShipOrder();
        order.setFromAddress(orderSenderVO.getAddress());
        order.setSenderName(orderSenderVO.getName());
        order.setSenderPhone(orderSenderVO.getPhone());
        order.setToAddress(orderReceiverVO.getAddress());
        order.setReceiverName(orderReceiverVO.getName());
        order.setReceiverPhone(orderReceiverVO.getPhone());
        // cargo info
        order.setCargo(cargo);
        order.setUserId(userId);

        // shipment info
        order.setOrderStatus(ShipOrder.Status.PENDING);
        order = orderRepository.save(order);
        List<Shipment> shipmentList = new ArrayList<>();
        var shipment = new Shipment();
        shipment.setTime(LocalDateTime.now());
        shipment.setType(Shipment.Type.ORDER);
        shipment.setDescription("订单创建");
        shipment.setShipOrder(order);

        shipmentList.add(shipment);
        order.setShipments(shipmentList);
        orderRepository.save(order);

        return BaseResponse.success("successfully create");
    }

    public BaseResponse updateOrderShipment(Integer userId, Integer orderId, String description, Shipment.Type type) {
        var order = orderRepository.findOrderByIdAndUserId(orderId, userId);
        if (order == null) {
            return BaseResponse.error("订单不存在");
        }

        // 完成运送的订单不允许再次修改
        if (order.getOrderStatus() == ShipOrder.Status.DELIVERED) {
            return BaseResponse.error("订单已完成，不允许修改");
        }

        if (type == Shipment.Type.PROCESSING || type == Shipment.Type.IN_TRANSIT) {
            order.setOrderStatus(ShipOrder.Status.DELIVERING);
        } else if (type == Shipment.Type.DELIVERED) {
            order.setOrderStatus(ShipOrder.Status.DELIVERED);
        }
        var shipment = new Shipment();
        shipment.setTime(LocalDateTime.now());
        shipment.setType(type);
        shipment.setDescription(description);
        shipment.setShipOrder(order);
        order.getShipments().add(shipment);
        orderRepository.save(order);

        return BaseResponse.success("successfully update");
    }

    public ShipOrder getOrder(Integer id, Integer userId) {
        return orderRepository.findByIdAndUserIdOrderByCreateTimeDesc(id, userId);
    }

    public List<ShipOrder> getOrders(Integer userId) {
        return orderRepository.findAllByUserIdOrderByCreateTimeDesc(userId);
    }
}
