package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "message")
public class MessageEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String message;

    private String type;
//
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
//
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;

    @OneToMany(mappedBy = "message")
    private Collection<AttachMessageEntity> attachMessageEntities;

}
