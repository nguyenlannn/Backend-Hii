package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_chanel")
public class User_ChannelEntity extends BaseEntity {

    private String name;

    @Column(columnDefinition = "bit")
    private String isActive;
//
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
//
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;
}
