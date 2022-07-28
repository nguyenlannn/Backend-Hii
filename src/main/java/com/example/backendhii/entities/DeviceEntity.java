package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "device")
public class DeviceEntity extends BaseEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String userAgent;

    @Column(nullable = false, columnDefinition = "text")
    private String accessToken;

    @Column(nullable = false, columnDefinition = "text")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
