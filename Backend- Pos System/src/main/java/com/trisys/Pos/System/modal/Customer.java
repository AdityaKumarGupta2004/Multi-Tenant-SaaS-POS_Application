package com.trisys.Pos.System.modal;
import com.trisys.Pos.System.domain.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private String address;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @PastOrPresent
    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String profileImageId;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}