package com.company.student_management_system.service.mapper;

import com.company.student_management_system.dto.StudentDto;
import com.company.student_management_system.models.Student;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDto(Student student);

    @Mapping(target = "paymentTransactionsList", ignore = true)
    Student toEntity(StudentDto dto);

    List<StudentDto> toDtoList(List<Student> students);

    Student updateStudent(@MappingTarget Student student, StudentDto studentDto);

}
