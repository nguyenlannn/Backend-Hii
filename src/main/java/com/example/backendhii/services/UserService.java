package com.example.backendhii.services;

import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.EditUserConsumeDto;
import com.example.backendhii.dto.consume.LoginConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {

    UserProduceDto register(UserConsumeDto userConsumeDto);

    void active(ActiveUserConsumeDto activeUserConsumeDto);

    String getEmailFromAccessToken();

    UserProduceDto editUser(EditUserConsumeDto editConsumeDto);

    UserProduceDto uploadImage(MultipartFile multipartFile) throws IOException;

    void getCodePassword(LoginConsumeDto loginConsumeDto) throws MessagingException;
    UserProduceDto resetPassword(UserConsumeDto userConsumeDto);
}
