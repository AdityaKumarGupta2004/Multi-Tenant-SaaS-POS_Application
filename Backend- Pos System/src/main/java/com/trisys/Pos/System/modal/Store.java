package com.trisys.Pos.System.modal;

import com.trisys.Pos.System.domain.StoreStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(
        name = "stores"
)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @OneToOne
    private User admin;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @PastOrPresent
    private LocalDateTime updatedAt;

    private String description;

    private String type;

    private StoreStatus status;

    @Embedded
    private StoreContact contact = new StoreContact();

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}