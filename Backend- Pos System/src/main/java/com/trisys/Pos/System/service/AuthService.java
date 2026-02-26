package com.trisys.Pos.System.service;

import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDTO userDTO) throws UserException;
    AuthResponse login(UserDTO userDTO) throws UserException;

}