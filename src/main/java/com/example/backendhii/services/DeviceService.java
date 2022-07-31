package com.example.backendhii.services;

import com.example.backendhii.dto.produce.TokenProduceDto;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {

    TokenProduceDto refreshToken(HttpServletRequest request);

    void updateToken(HttpServletRequest request, TokenProduceDto tokenProduceDto, String email);
}
