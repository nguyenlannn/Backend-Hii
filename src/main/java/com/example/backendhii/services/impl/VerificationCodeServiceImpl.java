package com.example.backendhii.services.impl;

import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.entities.VerificationCodeEntity;
import com.example.backendhii.repository.VerificationCodeRepository;
import com.example.backendhii.services.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository mVerificationCodeRepository;

    @Override
    public VerificationCodeEntity save(UserEntity userEntity) {
        String random = RandomStringUtils.random(6, "1234567890");
        VerificationCodeEntity verificationCodeEntity = VerificationCodeEntity.builder()
                .code(Integer.parseInt(random))
                .user(userEntity)
                .build();
        mVerificationCodeRepository.save(verificationCodeEntity);
        return verificationCodeEntity;
    }
}
