package com.trisys.Pos.System.modal;

import com.trisys.Pos.System.domain.UserRole;
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
@EqualsAndHashCode
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "profile_image_id_unique", columnNames = "profileImageId")
        }
)
public class User {

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

     @ManyToOne
     private Store store;
//
//     @ManyToOne
//     private Branch branch;

     private String phoneNumber;

     private String address;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private Gender gender;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserRole role;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @PastOrPresent
    private LocalDateTime updatedAt;

    @PastOrPresent
    private LocalDateTime lastLoginAt;

    @Column(unique = true)
    private String profileImageId;

    @Column(nullable = false)
    private String password;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}