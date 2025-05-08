package com.company.student_management_system.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpApiResponse<T> {
    private int statusCode;
    private boolean success;
    private T data;
    private String message;
}
