package com.paypilot.paypilot.service;

import com.paypilot.paypilot.constants.OrderStatus;
import com.paypilot.paypilot.constants.PaymentLandingPage;
import com.paypilot.paypilot.dto.*;
import com.paypilot.paypilot.entity.Order;
import com.paypilot.paypilot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final PaypalHttpClient paypalHttpClient;

    public CreateOrderResponseDto createOrder(OrderDto orderDto) throws Exception {
        PaypalAppContextDto defaultContext = PaypalAppContextDto.getDefaultContext();
        orderDto.setApplicationContext(defaultContext);
        orderDto.getPurchaseUnits().get(0).getAmount()
                .setBreakdown(BreakdownDto.of(orderDto));
        CreateOrderResponseDto orderResponse = paypalHttpClient.createOrder(orderDto);
        Order order = Order.createOrder(orderResponse);
        orderRepository.save(order);
        return orderResponse;
    }

    public void approvedOrder(String orderId) {
        Order targetOrder = orderRepository.findByPaypalOrderId(orderId);
        targetOrder.setPaypalOrderStatus(OrderStatus.APPROVED.toString());
        orderRepository.save(targetOrder);
    }

    public OrderDetailsResponseDto getOrderDetails(String orderId) throws Exception {
        return paypalHttpClient.getOrderDetails(orderId);
    }
}
