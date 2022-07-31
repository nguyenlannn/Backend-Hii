package com.example.backendhii.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backendhii.dto.produce.TokenProduceDto;
import com.example.backendhii.entities.DeviceEntity;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.exceptions.UnauthorizedException;
import com.example.backendhii.repository.DeviceRepository;
import com.example.backendhii.repository.UserRepository;
import com.example.backendhii.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final UserRepository mUserRepository;
    private final DeviceRepository mDeviceRepository;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Value("${JWT_TIME_ACCESS_TOKEN}")
    private Long JWT_TIME_ACCESS_TOKEN;

    @Override
    public TokenProduceDto refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                if (decodedJWT.getClaim("type").asString().equalsIgnoreCase("refresh")) {
                    UserEntity userEntity = mUserRepository.findByEmail(decodedJWT.getSubject());
                    TokenProduceDto tokenProduceDto = TokenProduceDto.builder()
                            .accessToken(JWT.create()
                                    .withSubject(userEntity.getEmail())
                                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TIME_ACCESS_TOKEN))
                                    .withIssuer(request.getRequestURL().toString())
                                    .withClaim(
                                            "roles",
                                            userEntity.getRoles().stream().map(o -> o.getName().toString()).collect(Collectors.toList()))
                                    .withClaim("type", "access")
                                    .sign(algorithm))
                            .refreshToken(refreshToken)
                            .build();
                    updateAccessToken(request, tokenProduceDto);
                    return tokenProduceDto;
                } else {
                    throw new UnauthorizedException("unauthorized");
                }
            } catch (Exception exception) {
                throw new UnauthorizedException(exception.getMessage());
            }
        } else {
            throw new UnauthorizedException("unauthorized");
        }
    }

    @Override
    public void updateToken(HttpServletRequest request, TokenProduceDto tokenProduceDto, String email) {
        UserEntity userEntity = mUserRepository.findByEmail(email);
        DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndAccessToken(
                request.getHeader(USER_AGENT),
                tokenProduceDto.getAccessToken());
        if (Objects.nonNull(deviceEntity)) {
            deviceEntity.setAccessToken(tokenProduceDto.getAccessToken());
            deviceEntity.setRefreshToken(tokenProduceDto.getRefreshToken());
        } else {
            deviceEntity = DeviceEntity.builder()
                    .userAgent(request.getHeader(USER_AGENT))
                    .accessToken(tokenProduceDto.getAccessToken())
                    .refreshToken(tokenProduceDto.getRefreshToken())
                    .user(userEntity)
                    .build();
        }
        mDeviceRepository.save(deviceEntity);
    }

    public void updateAccessToken(HttpServletRequest request, TokenProduceDto tokenProduceDto) {
        DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndRefreshToken(
                request.getHeader(USER_AGENT),
                tokenProduceDto.getRefreshToken());
        if (Objects.nonNull(deviceEntity)) {
            deviceEntity.setAccessToken(tokenProduceDto.getAccessToken());
            mDeviceRepository.save(deviceEntity);
        } else {
            throw new UnauthorizedException("expired version");
        }
    }
}
