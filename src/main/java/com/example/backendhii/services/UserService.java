package com.example.backendhii.services;

import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.EditUserConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserProduceDto register(UserConsumeDto userConsumeDto);

    void active(ActiveUserConsumeDto activeUserConsumeDto);

    String getEmailFromAccessToken(HttpServletRequest request);

    UserProduceDto editUser(EditUserConsumeDto editConsumeDto, HttpServletRequest request);
}
