package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import com.example.backendhii.entities.ChannelEntity;
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
public class ChannelHistoryProduceDto extends BaseProduceDto<Long> {

    private String history;

    private ChannelEntity channel;
}
