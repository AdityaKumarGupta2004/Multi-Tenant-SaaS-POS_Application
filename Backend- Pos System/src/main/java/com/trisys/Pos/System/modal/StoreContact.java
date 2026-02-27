package com.trisys.Pos.System.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreContact {

    @Column(unique = true, nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String phone;

    @Email(message = "Email should be valid")
    private String email;
}