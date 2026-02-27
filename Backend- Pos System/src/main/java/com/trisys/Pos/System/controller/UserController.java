package com.trisys.Pos.System.controller;


import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.mapper.UserMapper;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.payload.response.ApiResponse;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) throws UserException {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new UserException("User not found");
        }

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

     @DeleteMapping("/{id}")
     public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) throws UserException {
         userService.deleteUser(id);
         ApiResponse apiResponse = new ApiResponse();
         apiResponse.setMessage("User deleted successfully");

         return ResponseEntity.ok(apiResponse);
     }

}