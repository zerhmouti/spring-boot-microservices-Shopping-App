package com.zerhmouti.orderservice.repository;

import com.zerhmouti.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
