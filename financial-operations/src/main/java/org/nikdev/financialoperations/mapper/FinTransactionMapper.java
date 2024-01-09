package org.nikdev.financialoperations.mapper;

import org.mapstruct.Mapper;
import org.nikdev.entityservice.entity.FinOperationEntity;
import org.nikdev.financialoperations.dto.response.FinOperationOutDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FinTransactionMapper {


    List<FinOperationOutDto> toDoFinOperationDtoList(List<FinOperationEntity> finOperationEntity);


}
