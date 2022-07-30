package com.example.backendhii.mapper;

import com.example.backendhii.dto.produce.UserProduceDto;
import com.example.backendhii.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CustomMapper {

    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract UserProduceDto toUserProduceDto(UserEntity userEntity);
}
