package com.example.backendhii.services.impl;

import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.entities.VerificationCodeEntity;
import com.example.backendhii.exceptions.BadRequestException;
import com.example.backendhii.repository.VerificationCodeRepository;
import com.example.backendhii.services.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository mVerificationCodeRepository;
    private final JavaMailSender mJavaMailSender;

    @Override
    public VerificationCodeEntity save(UserEntity userEntity) {
        String random = RandomStringUtils.random(6, "1234567890");
        VerificationCodeEntity verificationCodeEntity = VerificationCodeEntity.builder()
                .code(Integer.parseInt(random))
                .user(userEntity)
                .build();
        mVerificationCodeRepository.save(verificationCodeEntity);
        sendEmailContainVerificationCode(userEntity.getEmail(), Integer.parseInt(random));
        return verificationCodeEntity;
    }

    @Override
    public void sendEmailContainVerificationCode(String toEmail, Integer verificationCode) {
        MimeMessage message = mJavaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            message.setContent("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <div style=\"margin: auto; width: 50%; border: solid;\">\n" +
                    "        <div style=\"text-align: center; border-bottom: solid; background-color: beige;\">\n" +
                    "            <i style=\"font-size: 35px; font-weight: bold;\">Ben</i><i\n" +
                    "                style=\"font-size: 35px; font-weight: bold; color: gold;\">Shop</i>\n" +
                    "        </div>\n" +
                    "        <div style=\"text-align: left; margin-left: 10%;\">\n" +
                    "            <p style=\"font-size: 14px; line-height: 140%;\">Dear Sir</p>\n" +
                    "            <p style=\"font-size: 14px; line-height: 140%;\">This is a new password for your account:\n" +
                    verificationCode +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>", "text/html; charset=UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Lan Ngốc");
            this.mJavaMailSender.send(message);
        } catch (MessagingException exception) {
            throw new BadRequestException("sen email fail");
        }
    }
}
