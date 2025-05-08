package com.company.student_management_system.dto;

import com.company.student_management_system.models.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private UserRole userRole;
}
