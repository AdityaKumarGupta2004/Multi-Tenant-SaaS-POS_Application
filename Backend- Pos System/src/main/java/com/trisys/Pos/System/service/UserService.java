package com.trisys.Pos.System.service;

import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.modal.User;

import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;

    User getCurrentUser() throws UserException;

    User getUserByEmail(String email) throws UserException;

    User getUserById(Long userId) throws UserException;

    List<User> getAllUsers();

    void deleteUser(Long userId) throws UserException;
}