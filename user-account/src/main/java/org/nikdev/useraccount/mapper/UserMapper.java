package org.nikdev.useraccount.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nikdev.entityservice.entity.UserEntity;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.dto.response.UserOutDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.roleName")
    UserOutDto toDto(UserEntity userEntity);

    UserIncomeOutDto toDoIncomeDto(UserEntity userEntity);
}
