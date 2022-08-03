package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import com.example.backendhii.entities.ChannelEntity;
import com.example.backendhii.entities.UserEntity;
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
public class UserChannelProduceDto extends BaseProduceDto<Long> {

    private String name;

    private Boolean isActive;

    private UserEntity user;

    private ChannelEntity channel;
}
