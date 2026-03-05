package com.trisys.Pos.System.modal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trisys.Pos.System.domain.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "refunds"
)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Order order;

    private String reason;

    private Double amount;

    @ManyToOne
    @JsonIgnore
    private ShiftReport shiftReport;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Branch branch;

    private LocalDateTime createdAt;

    private PaymentType paymentType;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}