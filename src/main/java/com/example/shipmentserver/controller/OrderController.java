package com.example.shipmentserver.controller;

import com.example.shipmentserver.component.BaseResponse;
import com.example.shipmentserver.component.UserInfo;
import com.example.shipmentserver.service.ShipOrderService;
import com.example.shipmentserver.vo.OrderShipmentVO;
import com.example.shipmentserver.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    @Autowired
    private ShipOrderService orderService;

    @GetMapping("{id}")
    public BaseResponse getOrder(@PathVariable Integer id) {
        return BaseResponse.success(orderService.getOrder(id, Integer.valueOf(UserInfo.get("id"))));
    }

    @GetMapping("")
    public BaseResponse getOrders() {
        return BaseResponse.success(orderService.getOrders(Integer.valueOf(UserInfo.get("id"))));
    }

    @PostMapping("")
    public BaseResponse createOrder(@RequestBody OrderVO orderVO) {
        return orderService.createOrder(Integer.valueOf(UserInfo.get("id")),
                orderVO.getSender(),
                orderVO.getReceiver(),
                orderVO.getCargo());
    }

    @PostMapping("/order-price")
    public BaseResponse getOrderPrice(@RequestBody OrderVO orderVO) {
        return orderService.getOrderPrice(orderVO.getSender(),
                orderVO.getReceiver(),
                orderVO.getCargo());
    }

    @PostMapping("/shipments")
    public BaseResponse updateOrderShipment(@RequestBody OrderShipmentVO orderShipmentVO) {

        return orderService.updateOrderShipment(Integer.valueOf(UserInfo.get("id")),
                orderShipmentVO.getOrderId(),
                orderShipmentVO.getDescription(),
                orderShipmentVO.getType());
    }
}
