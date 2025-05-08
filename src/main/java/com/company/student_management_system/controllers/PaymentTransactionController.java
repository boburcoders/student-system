package com.company.student_management_system.controllers;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.PaymentTransactionDto;
import com.company.student_management_system.service.PaymentTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentTransactionController {
    private final PaymentTransactionService transactionService;

    @PostMapping("/students/{id}/transactions")
    public HttpApiResponse<PaymentTransactionDto> createTransaction(@PathVariable Long id, @RequestBody @Valid PaymentTransactionDto dto) {
        return this.transactionService.createTransaction(id, dto);
    }

    @GetMapping("/transactions")
    public HttpApiResponse<List<PaymentTransactionDto>> getAllTransactions(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam Double minAmount,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "20", required = false) int size
    ) {
        return this.transactionService.getAllTransactions(from, to, minAmount, page, size);
    }

    @GetMapping("/transactions/{id}")
    public HttpApiResponse<PaymentTransactionDto> getTransactionById(@PathVariable Long id) {
        return this.transactionService.getTransactionById(id);
    }

    @GetMapping("/students/{studentId}/transactions")
    public HttpApiResponse<List<PaymentTransactionDto>> getTransactionsByStudent(@PathVariable Long studentId) {
        return transactionService.getTransactionsByStudent(studentId);
    }

    @PutMapping("/transactions/{id}")
    public HttpApiResponse<PaymentTransactionDto> updateTransactionById(@PathVariable Long id,
                                                                        @RequestBody PaymentTransactionDto dto) {
        return this.transactionService.updateTransactionById(id, dto);
    }

    @DeleteMapping("/transactions/{id}")
    public HttpApiResponse<String> deleteTransactionById(@PathVariable Long id) {
        return this.transactionService.deleteTransactionById(id);
    }
}
