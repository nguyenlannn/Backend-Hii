package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import com.example.backendhii.entities.MessageEntity;
import com.example.backendhii.entities.UserChannelEntity;
import com.example.backendhii.entities.UserHistoryEntity;
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

    private Collection<UserHistoryEntity> userHistories;

    private Collection<UserChannelEntity> userChannels;

    private Collection<MessageEntity> messages;

    private Boolean isActive;
}