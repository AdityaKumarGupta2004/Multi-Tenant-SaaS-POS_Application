package com.trisys.Pos.System.repository;
import com.trisys.Pos.System.modal.Order;
import com.trisys.Pos.System.modal.User;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByBranchId(Long branchId);

    List<Order> findByCashierId(Long cashierId);

    List<Order> findByBranchIdAndCreatedAtBetween(Long branchId, LocalDateTime from, LocalDateTime to);

    List<Order> findByCashierAndCreatedAtBetween(User cashier, @PastOrPresent LocalDateTime createdAt, @PastOrPresent LocalDateTime createdAt2);

    List<Order> findTop5ByBranchIdOrderByCreatedAtDesc(Long branchId);
}