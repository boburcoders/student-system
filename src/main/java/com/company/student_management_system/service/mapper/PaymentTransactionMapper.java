package com.company.student_management_system.service.mapper;

import com.company.student_management_system.dto.PaymentTransactionDto;
import com.company.student_management_system.models.PaymentTransactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {

    @Mapping(target = "studentId", expression = "java(entity.getStudent().getId())")
    @Mapping(target = "dateTime", ignore = true)
    PaymentTransactionDto toDto(PaymentTransactions entity);

    PaymentTransactions toEntity(PaymentTransactionDto dto);

    PaymentTransactions updatePaymentTransaction(@MappingTarget PaymentTransactions entity, PaymentTransactionDto dto);

    List<PaymentTransactionDto> toDtoList(List<PaymentTransactions> entities);

}
