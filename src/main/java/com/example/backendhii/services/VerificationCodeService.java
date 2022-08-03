package com.example.backendhii.services;

import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.entities.VerificationCodeEntity;

public interface VerificationCodeService {

    VerificationCodeEntity save(UserEntity userEntity);
}
