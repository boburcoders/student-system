package com.company.student_management_system.service;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    HttpApiResponse<UserDto> registerUser(UserDto dto);

    HttpApiResponse<UserDto> loginUser(UserDto dto);
}
