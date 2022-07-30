package com.example.backendhii.mapper;

import com.example.backendhii.dto.produce.RoleProduceDto;
import com.example.backendhii.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements CustomMapper {

    @Mapping(target = "users", ignore = true)
    public abstract RoleProduceDto roleProduceDto(RoleEntity roleEntity);

}
