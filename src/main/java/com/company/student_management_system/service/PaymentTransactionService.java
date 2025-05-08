package com.company.student_management_system.service;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.PaymentTransactionDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Primary
public interface PaymentTransactionService {
    HttpApiResponse<PaymentTransactionDto> createTransaction(Long id, PaymentTransactionDto dto);

    HttpApiResponse<List<PaymentTransactionDto>> getAllTransactions(LocalDate from, LocalDate to, Double minAmount, int page, int size);

    HttpApiResponse<PaymentTransactionDto> getTransactionById(Long id);

    HttpApiResponse<PaymentTransactionDto> updateTransactionById(Long id, PaymentTransactionDto dto);

    HttpApiResponse<String> deleteTransactionById(Long id);

    HttpApiResponse<List<PaymentTransactionDto>> getTransactionsByStudent(Long studentId);
}
