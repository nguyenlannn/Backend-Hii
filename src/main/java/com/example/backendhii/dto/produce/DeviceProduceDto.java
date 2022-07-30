package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DeviceProduceDto extends BaseProduceDto<Long> {

    private String userAgent;

    private UserProduceDto user;
}
