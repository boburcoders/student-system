package com.company.student_management_system.service.impl;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.StudentDto;
import com.company.student_management_system.exceptions.ItemNotFoundException;
import com.company.student_management_system.models.Student;
import com.company.student_management_system.repository.StudentRepository;
import com.company.student_management_system.repository.UserRepository;
import com.company.student_management_system.service.StudentService;
import com.company.student_management_system.service.mapper.StudentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public HttpApiResponse<StudentDto> createStudent(StudentDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return HttpApiResponse.<StudentDto>builder()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .success(false)
                    .message("Username already exists")
                    .build();
        }

        try {
            Student entity = studentMapper.toEntity(dto);
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity = studentRepository.save(entity);

            return HttpApiResponse.<StudentDto>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .success(true)
                    .message("Student created successfully")
                    .data(studentMapper.toDto(entity))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create student: " + e.getMessage(), e);
        }
    }


    @Override
    public HttpApiResponse<StudentDto> getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Student not found with id: " + id));

        return HttpApiResponse.<StudentDto>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("OK")
                .data(studentMapper.toDto(student))
                .build();
    }

    @Override
    public HttpApiResponse<List<StudentDto>> getAllStudent(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Student> studentList = studentRepository.findAll(pageable).getContent();
        if (studentList.isEmpty()) {
            return HttpApiResponse.<List<StudentDto>>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Students not found")
                    .build();
        }
        List<StudentDto> dtoList = studentMapper.toDtoList(studentList);
        return HttpApiResponse.<List<StudentDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("OK")
                .data(dtoList)
                .build();
    }

    @Override
    public HttpApiResponse<StudentDto> updateStudentById(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Student not found with id: " + id));

        Student updateStudent = studentMapper.updateStudent(student, dto);

        studentRepository.save(updateStudent);

        return HttpApiResponse.<StudentDto>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("OK")
                .data(studentMapper.toDto(updateStudent))
                .build();
    }

    @Override
    public HttpApiResponse<String> deleteStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Student not found with id: " + id));

        studentRepository.delete(student);

        return HttpApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("Student deleted successfully")
                .build();
    }
}
