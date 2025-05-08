package com.company.student_management_system.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends Users {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int courseNumber;
    private String groupName;
    private String facultyName;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<PaymentTransactions> paymentTransactionsList;


}
