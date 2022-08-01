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
@Entity(name = "chanel_history")
public class Channel_HistoryEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String history;
//
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;
}
