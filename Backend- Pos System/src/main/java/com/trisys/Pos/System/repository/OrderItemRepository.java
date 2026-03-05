package com.trisys.Pos.System.repository;
import com.trisys.Pos.System.modal.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}