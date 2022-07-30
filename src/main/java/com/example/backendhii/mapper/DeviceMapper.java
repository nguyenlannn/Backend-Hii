package com.example.backendhii.mapper;

import com.example.backendhii.dto.produce.DeviceProduceDto;
import com.example.backendhii.entities.DeviceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class DeviceMapper implements CustomMapper {

    public abstract DeviceProduceDto deviceProduceDto(DeviceEntity deviceEntity);
}
