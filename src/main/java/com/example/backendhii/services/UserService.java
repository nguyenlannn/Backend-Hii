package com.example.backendhii.services;

import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.EditConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;
import com.example.backendhii.entities.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserProduceDto register(UserConsumeDto userConsumeDto);

    void active(ActiveUserConsumeDto activeUserConsumeDto);

    String getEmailFromAccessToken(HttpServletRequest request);

    UserEntity getUserFromAccessToken(HttpServletRequest request);

    UserProduceDto editUser(EditConsumeDto editConsumeDto, HttpServletRequest request);
}
