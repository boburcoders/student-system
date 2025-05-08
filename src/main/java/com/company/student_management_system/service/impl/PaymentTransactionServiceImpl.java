package com.company.student_management_system.service.impl;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.PaymentTransactionDto;
import com.company.student_management_system.exceptions.ItemNotFoundException;
import com.company.student_management_system.models.PaymentTransactions;
import com.company.student_management_system.models.Student;
import com.company.student_management_system.repository.PaymentTransactionRepository;
import com.company.student_management_system.repository.StudentRepository;
import com.company.student_management_system.service.PaymentTransactionService;
import com.company.student_management_system.service.mapper.PaymentTransactionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository transactionRepository;
    private final StudentRepository studentRepository;
    private final PaymentTransactionMapper transactionMapper;

    @Override
    public HttpApiResponse<PaymentTransactionDto> createTransaction(Long id, PaymentTransactionDto dto) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException("Student not found with id: " + id));

            PaymentTransactions entity = transactionMapper.toEntity(dto);
            entity.setStudent(student);
            transactionRepository.save(entity);

            if (student.getPaymentTransactionsList() == null) {
                student.setPaymentTransactionsList(new ArrayList<>());
            }
            student.getPaymentTransactionsList().add(entity);

            studentRepository.save(student);

            return HttpApiResponse.<PaymentTransactionDto>builder()
                    .success(true)
                    .message("Payment transaction created successfully")
                    .statusCode(HttpStatus.CREATED.value())
                    .data(transactionMapper.toDto(entity))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create transaction: " + e);
        }
    }

    @Override
    public HttpApiResponse<List<PaymentTransactionDto>> getAllTransactions(LocalDate from, LocalDate to, Double minAmount, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PaymentTransactions> transactionsWithFilter = this.transactionRepository.findTransactionsWithFilter(from, to, minAmount, pageable);

        List<PaymentTransactionDto> paymentTransactionDtoList = transactionsWithFilter.getContent().stream()
                .map(transactionMapper::toDto)
                .toList();

        return HttpApiResponse.<List<PaymentTransactionDto>>builder()
                .success(true)
                .message("All payment transactions")
                .statusCode(HttpStatus.OK.value())
                .data(paymentTransactionDtoList)
                .build();
    }

    @Override
    public HttpApiResponse<PaymentTransactionDto> getTransactionById(Long id) {

        PaymentTransactions paymentTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Transaction not found with id: " + id));

        return HttpApiResponse.<PaymentTransactionDto>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("OK")
                .data(transactionMapper.toDto(paymentTransaction))
                .build();
    }

    @Override
    public HttpApiResponse<List<PaymentTransactionDto>> getTransactionsByStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ItemNotFoundException("Student not found with id: " + studentId));

        List<PaymentTransactions> allByStudentId = transactionRepository.findAllByStudentId(studentId);

        if (allByStudentId.isEmpty()) {
            return HttpApiResponse.<List<PaymentTransactionDto>>builder()
                    .success(true)
                    .statusCode(HttpStatus.OK.value())
                    .message("Payment transactions list empty")
                    .build();
        }

        return HttpApiResponse.<List<PaymentTransactionDto>>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("OK")
                .data(this.transactionMapper.toDtoList(allByStudentId))
                .build();
    }

    @Override
    public HttpApiResponse<PaymentTransactionDto> updateTransactionById(Long id, PaymentTransactionDto dto) {
        PaymentTransactions paymentTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Transaction not found with id: " + id));

        PaymentTransactions updatedPaymentTransaction = this.transactionMapper.updatePaymentTransaction(paymentTransaction, dto);

        transactionRepository.save(updatedPaymentTransaction);

        return HttpApiResponse.<PaymentTransactionDto>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("Payment transaction updated successfully")
                .data(transactionMapper.toDto(updatedPaymentTransaction))
                .build();
    }

    @Override
    public HttpApiResponse<String> deleteTransactionById(Long id) {
        PaymentTransactions paymentTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Transaction not found with id: " + id));

        transactionRepository.deleteById(id);

        return HttpApiResponse.<String>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("Payment transaction deleted successfully")
                .build();
    }
}
