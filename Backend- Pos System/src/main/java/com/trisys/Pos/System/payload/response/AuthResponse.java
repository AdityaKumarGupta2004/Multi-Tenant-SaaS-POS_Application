package com.trisys.Pos.System.payload.response;

import com.trisys.Pos.System.payload.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDTO user;

}
