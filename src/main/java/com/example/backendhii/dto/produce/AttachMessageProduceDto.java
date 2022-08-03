package com.example.backendhii.dto.produce;

import com.example.backendhii.basess.BaseProduceDto;
import com.example.backendhii.entities.MessageEntity;
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
public class AttachMessageProduceDto extends BaseProduceDto<Long> {

    private String path;

    private String name;

    private Integer size;

    private MessageEntity message;
}