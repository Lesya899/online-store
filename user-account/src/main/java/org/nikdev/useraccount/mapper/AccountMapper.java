package org.nikdev.useraccount.mapper;

import org.mapstruct.Mapper;

import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.dto.response.UserAccountOutDto;
import org.nikdev.useraccount.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    UserAccountOutDto toDto(Account userAccount);

    UserIncomeOutDto toDoIncomeDto(Account userAccount);
}
