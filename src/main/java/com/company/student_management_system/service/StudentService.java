package com.company.student_management_system.service;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    HttpApiResponse<StudentDto> createStudent(StudentDto dto);

    HttpApiResponse<StudentDto> getStudentById(Long id);

    HttpApiResponse<List<StudentDto>> getAllStudent(Integer page, Integer size);

    HttpApiResponse<StudentDto> updateStudentById(Long id, StudentDto dto);

    HttpApiResponse<String> deleteStudentById(Long id);
}
