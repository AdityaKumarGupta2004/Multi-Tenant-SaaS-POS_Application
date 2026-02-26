package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.configuration.JwtProvider;
import com.trisys.Pos.System.domain.UserRole;
import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.mapper.UserMapper;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.payload.response.AuthResponse;
import com.trisys.Pos.System.repository.UserRepository;
import com.trisys.Pos.System.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDTO userDTO) throws UserException {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new UserException("Email is already registered");
        }
        if (userDTO.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("ADMIN role is not allowed");
        }
        User newUser = new User();
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setAddress(userDTO.getAddress());
        // newUser.setGender(userDTO.getGender());
        newUser.setDateOfBirth(userDTO.getDateOfBirth());
        newUser.setRole(userDTO.getRole());
        newUser.setRegisteredAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setLastLoginAt(LocalDateTime.now());
        newUser.setProfileImageId(userDTO.getProfileImageId());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        System.out.println("New User: " + newUser);
        User savedUser = userRepository.save(newUser);
        System.out.println(savedUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse();
         authResponse.setJwt(jwt);
         authResponse.setMessage("Registered successfully");
         authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserDTO userDTO) throws UserException {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        Authentication auth = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String role = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(auth);

        User user = userRepository.findByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Logged in successfully");
        authResponse.setUser(UserMapper.toDTO(user));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);

        if (userDetails == null) {
            throw new UserException("Email " + email + " does not exists");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


    }
}