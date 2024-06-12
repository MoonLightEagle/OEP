package com.oep.controller;

import com.oep.domain.User;
import com.oep.dto.TokenResponse;
import com.oep.dto.request.AuthenticationRequest;
import com.oep.dto.request.CreateUserRequest;
import com.oep.dto.response.UserResponse;
import com.oep.service.UserService;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:63344/")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse create(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }

    @PostMapping("/auth")
    public UserResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.authenticate(authenticationRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserResponse> getAll(@QuerydslPredicate(root = User.class) Predicate filters,
                                     @PageableDefault Pageable pageable) {
        return userService.getAll(filters, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody @Valid CreateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
