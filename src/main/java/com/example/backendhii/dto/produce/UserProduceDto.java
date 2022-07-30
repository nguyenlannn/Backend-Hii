package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserProduceDto extends BaseProduceDto<Long> {

    private String email;

    private Collection<DeviceProduceDto> devices;

    private Collection<RoleProduceDto> roles;
}