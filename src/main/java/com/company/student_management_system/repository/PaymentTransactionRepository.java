package com.company.student_management_system.repository;

import com.company.student_management_system.models.PaymentTransactions;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactions, Long> {

    List<PaymentTransactions> findAllByDateTimeBetween(LocalDate from, LocalDate to);

    List<PaymentTransactions> findAllByStudentId(Long studentId);

    @Query("SELECT p FROM PaymentTransactions p WHERE " +
            "(p.dateTime >= :from AND p.dateTime <= :to) " +
            "AND p.amount >= :minAmount")
    Page<PaymentTransactions> findTransactionsWithFilter(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("minAmount") Double minAmount,
            Pageable pageable);
}
