package com.trisys.Pos.System.controller;


import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.mapper.UserMapper;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO>  getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserByEmail(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
