package com.company.student_management_system.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudentDto extends UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int courseNumber;
    private String groupName;
    private String facultyName;

    private List<PaymentTransactionDto> paymentTransactionDtoList;
}
