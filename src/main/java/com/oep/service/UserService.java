package com.oep.service;

import com.oep.common.Role;
import com.oep.domain.User;
import com.oep.dto.TokenResponse;
import com.oep.dto.request.AuthenticationRequest;
import com.oep.dto.request.CreateUserRequest;
import com.oep.dto.response.UserResponse;
import com.oep.exception.ServiceException;
import com.oep.mapper.UserMapper;
import com.oep.repository.UserRepository;
import com.oep.security.UsersDetails;
import com.oep.security.auth.JwtService;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        User user = userMapper.toUser(request);

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ServiceException("User already registered");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new ServiceException("Password and repeat password does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_ADMIN);

        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setTokenResponse(getTokenResponse(user));

        return userResponse;
    }

    @Secured("ROLE_ADMIN")
    public Page<UserResponse> getAll(Predicate filters, Pageable pageable) {
        return userRepository.findAll(filters, pageable).map(userMapper::toUserResponse);
    }

    public UserResponse getById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponse).orElseThrow();
    }

    @Transactional
    public UserResponse update(Long id, CreateUserRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    userMapper.update(request, user);
                    return userMapper.toUserResponse(user);
                })
                .orElseThrow();
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private TokenResponse getTokenResponse(User user) {
        UsersDetails userDetails = new UsersDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000 * 60 * 60 * 24);
        return new TokenResponse(jwtToken, currentTime, convertMillisecondsToLocalDateTime(expirationTime));
    }

    public static LocalDateTime convertMillisecondsToLocalDateTime(long milliseconds) {
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public UserResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByEmailIgnoreCase(authenticationRequest.getEmail()).orElseThrow();

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new ServiceException("Phone number or password is incorrect!");
        }

        UserResponse userResponse = userMapper.toUserResponse(user);
        TokenResponse tokenResponse = getTokenResponse(user);
        userResponse.setTokenResponse(tokenResponse);
        return userResponse;

    }
}
