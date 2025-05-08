package com.company.student_management_system.service.impl;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.UserDto;
import com.company.student_management_system.models.Users;
import com.company.student_management_system.repository.UserRepository;
import com.company.student_management_system.service.UserService;
import com.company.student_management_system.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public HttpApiResponse<UserDto> registerUser(UserDto dto) {
        boolean existsByUsername = userRepository.existsByUsername(dto.getUsername());
        if (existsByUsername) {
            return HttpApiResponse.<UserDto>builder()
                    .success(false)
                    .message("Username is already taken!")
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        }
        Users entity = userMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.saveAndFlush(entity);
        return HttpApiResponse.<UserDto>builder()
                .success(true)
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully!")
                .data(userMapper.toDto(entity))
                .build();
    }

    @Override
    public HttpApiResponse<UserDto> loginUser(UserDto dto) {
        Users user = userRepository.findByUsername(dto.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return HttpApiResponse.<UserDto>builder()
                    .success(false)
                    .message("Invalid username or password!")
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
        return HttpApiResponse.<UserDto>builder()
                .success(true)
                .message("Login successful")
                .data(userMapper.toDto(user))
                .build();
    }

}
