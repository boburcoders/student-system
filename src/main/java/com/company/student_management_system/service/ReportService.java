package com.company.student_management_system.service;

import com.company.student_management_system.dto.HttpApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public interface ReportService {
    HttpApiResponse<Map<String, Object>> getSummary(LocalDate from, LocalDate to);
}
