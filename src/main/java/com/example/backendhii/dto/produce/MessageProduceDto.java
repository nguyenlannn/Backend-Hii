package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import com.example.backendhii.entities.AttachMessageEntity;
import com.example.backendhii.entities.ChannelEntity;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.enums.MessageEnum;
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
public class MessageProduceDto extends BaseProduceDto<Long> {

    private String message;

    private MessageEnum type;

    private UserEntity user;

    private ChannelEntity channel;

    private Collection<AttachMessageEntity> attachMessages;
}
