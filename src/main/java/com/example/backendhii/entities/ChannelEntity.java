package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chanel")
public class ChannelEntity extends BaseEntity {

    private String name;

    private String type;

    @OneToMany(mappedBy = "channel")
    private Collection<UserChannelEntity> userChannel;

    @OneToMany(mappedBy = "channel")
    private Collection<ChannelHistoryEntity> channelHistory;

    @OneToMany(mappedBy = "channel")
    private Collection<MessageEntity> message;
}
