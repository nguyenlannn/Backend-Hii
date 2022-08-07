package com.example.backendhii.services;

import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.entities.VerificationCodeEntity;

import javax.mail.MessagingException;

public interface VerificationCodeService {

    VerificationCodeEntity save(UserEntity userEntity);

    void sendEmailContainVerificationCode(String toEmail, Integer verificationCode) throws MessagingException;
}
