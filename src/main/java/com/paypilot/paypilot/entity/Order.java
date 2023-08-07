package com.paypilot.paypilot.entity;

import com.paypilot.paypilot.dto.CreateOrderResponseDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paypal_order_id")
    private String paypalOrderId;

    @Column(name = "paypal_order_status")
    private String paypalOrderStatus;

    public static Order createOrder(CreateOrderResponseDto orderResponseDto) {
        Order order = new Order();
        order.setPaypalOrderId(orderResponseDto.getId());
        order.setPaypalOrderStatus(orderResponseDto.getStatus().toString());
        return order;
    }

}