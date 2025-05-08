package com.company.student_management_system.controllers;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.UserDto;
import com.company.student_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public HttpApiResponse<UserDto> registerUser(@RequestBody @Valid UserDto dto) {
        return this.userService.registerUser(dto);
    }

    @PostMapping("/login")
    public HttpApiResponse<UserDto> loginUser(@RequestBody UserDto dto) {
        return this.userService.loginUser(dto);
    }

}
