package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import com.example.backendhii.enums.ChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chanel")
public class ChannelEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private ChannelEnum type;

    @OneToMany(mappedBy = "channel")
    private Collection<UserChannelEntity> userChannels;

    @OneToMany(mappedBy = "channel")
    private Collection<ChannelHistoryEntity> channelHistories;

    @OneToMany(mappedBy = "channel")
    private Collection<MessageEntity> messages;
}
