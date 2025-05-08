package com.company.student_management_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDto {
    private String errorPath;
    private Integer errorCode;
    private Object errorBody;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
