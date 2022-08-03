package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import com.example.backendhii.entities.ChannelHistoryEntity;
import com.example.backendhii.entities.MessageEntity;
import com.example.backendhii.entities.UserChannelEntity;
import com.example.backendhii.enums.ChannelEnum;
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
public class ChannelProduceDto extends BaseProduceDto<Long> {

    private String name;

    private ChannelEnum type;

    private Collection<UserChannelEntity> userChannels;

    private Collection<ChannelHistoryEntity> channelHistories;

    private Collection<MessageEntity> messages;
}
