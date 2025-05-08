package com.company.student_management_system.service.impl;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.models.PaymentTransactions;
import com.company.student_management_system.repository.PaymentTransactionRepository;
import com.company.student_management_system.service.ReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {
    private final PaymentTransactionRepository transactionRepository;

    @Override
    public HttpApiResponse<Map<String, Object>> getSummary(LocalDate from, LocalDate to) {
        List<PaymentTransactions> transactions = transactionRepository.findAllByDateTimeBetween(from, to);

        double totalAmount = transactions.stream()
                .mapToDouble(PaymentTransactions::getAmount)
                .sum();
        int totalTransactions = transactions.size();
        return HttpApiResponse.<Map<String, Object>>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .data(Map.of("TotalAmount", totalAmount, "TotalTransactions", totalTransactions))
                .message("OK")
                .build();
    }
}
