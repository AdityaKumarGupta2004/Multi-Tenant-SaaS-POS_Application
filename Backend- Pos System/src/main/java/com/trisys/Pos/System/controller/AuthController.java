package com.trisys.Pos.System.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.payload.response.AuthResponse;
import com.trisys.Pos.System.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")  // http://localhost:6000/auth/signup
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(authService.signup(userDTO));
    }


    @PostMapping("/login")  // http://localhost:6000/auth/login
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(authService.login(userDTO));
    }
}
