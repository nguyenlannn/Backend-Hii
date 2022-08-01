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
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "attach_message")
public class Attach_MessageEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String path;

    @Column(columnDefinition = "text")
    private String name;

    private Integer size;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageEntity message;
}
