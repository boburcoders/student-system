package com.company.student_management_system.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SecurityErrorDto {
    private String errorMessage;
    private int errorCode;
    private String errorPath;
    private LocalDateTime timestamp;

    public SecurityErrorDto(String errorMessage, int errorCode, String errorPath) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorPath = errorPath;
        this.timestamp = LocalDateTime.now();
    }
}
