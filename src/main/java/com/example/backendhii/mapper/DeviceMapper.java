package com.example.backendhii.mapper;

import com.example.backendhii.dto.produce.DeviceProduceDto;
import com.example.backendhii.entities.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class DeviceMapper implements CustomMapper {

    @Mapping(target = "users", ignore = true)
    public abstract DeviceProduceDto toDeviceProduceDto(DeviceEntity deviceEntity);
}
