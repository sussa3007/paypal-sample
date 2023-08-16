package com.paypilot.paypilot.controller;

import com.paypilot.paypilot.dto.CreateOrderResponseDto;
import com.paypilot.paypilot.dto.OrderDetailsResponseDto;
import com.paypilot.paypilot.dto.OrderDto;
import com.paypilot.paypilot.repository.OrderRepository;
import com.paypilot.paypilot.service.OrderService;
import com.paypilot.paypilot.service.PaypalHttpClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/checkout")
@RequiredArgsConstructor
@RestController
public class CheckoutController {

    private final OrderRepository orderRepository;

    private final PaypalHttpClient paypalHttpClient;

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> checkout(
            @RequestBody OrderDto orderDto
    ) throws Exception {
        CreateOrderResponseDto orderResponse = orderService.createOrder(orderDto);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        orderService.approvedOrder(orderId);
        return ResponseEntity.ok().body("Payment success");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) throws Exception {
        OrderDetailsResponseDto response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }

}
