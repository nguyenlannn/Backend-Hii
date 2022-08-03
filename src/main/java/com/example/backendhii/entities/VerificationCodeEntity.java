package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "verification_code")
public class VerificationCodeEntity extends BaseEntity {

    private Integer code;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
