package com.paypilot.paypilot.repository;

import com.paypilot.paypilot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByPaypalOrderId(String paypalOrderId);
}
