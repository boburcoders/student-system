package com.company.student_management_system.controllers;

import com.company.student_management_system.dto.HttpApiResponse;
import com.company.student_management_system.dto.StudentDto;
import com.company.student_management_system.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public HttpApiResponse<StudentDto> createStudent(@RequestBody @Valid StudentDto dto) {
        return this.studentService.createStudent(dto);
    }

    @GetMapping("/{id}")
    public HttpApiResponse<StudentDto> getStudentById(@PathVariable Long id) {
        return this.studentService.getStudentById(id);
    }

    @GetMapping
    public HttpApiResponse<List<StudentDto>> getAllStudent(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "20") Integer size) {
        return this.studentService.getAllStudent(page, size);
    }


    @PutMapping("/{id}")
    public HttpApiResponse<StudentDto> updateStudentById(@PathVariable Long id, @RequestBody StudentDto dto) {
        return this.studentService.updateStudentById(id, dto);
    }

    @DeleteMapping("/{id}")
    public HttpApiResponse<String> deleteStudentById(@PathVariable Long id) {
        return this.studentService.deleteStudentById(id);
    }
}
