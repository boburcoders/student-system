package com.company.student_management_system.service.mapper;

import com.company.student_management_system.dto.UserDto;
import com.company.student_management_system.models.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toEntity(UserDto userDto);

    UserDto toDto(Users users);


}
