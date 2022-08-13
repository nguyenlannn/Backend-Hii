package com.example.backendhii.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.enums.RoleEnum;
import com.example.backendhii.exceptions.BadRequestException;
import com.example.backendhii.mapper.UserMapper;
import com.example.backendhii.repository.RoleRepository;
import com.example.backendhii.repository.UserRepository;
import com.example.backendhii.services.UserService;
import com.example.backendhii.services.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;
    private final RoleRepository mRoleRepository;
    private final PasswordEncoder mPasswordEncoder;

    private final UserMapper mUserMapper;

    private final VerificationCodeService mVerificationCodeService;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    public void createAdmin(UserEntity userEntity) {
        userEntity.setRoles(mRoleRepository.findAll());
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setIsActive(true);
        mUserRepository.save(userEntity);
    }

    public void createUser(UserEntity userEntity) {
        userEntity.setRoles(mRoleRepository.findByName(RoleEnum.ROLE_USER));
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setIsActive(true);
        mUserRepository.save(userEntity);
    }

    @Override
    public UserProduceDto register(UserConsumeDto userConsumeDto) {
        UserEntity userEntity = userConsumeDto.toUserEntity();
        if (mUserRepository.existsByEmail(userConsumeDto.getEmail())) {
            throw new BadRequestException("email already exist");
        }
        userEntity.setRoles(mRoleRepository.findByName(RoleEnum.ROLE_USER));
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setIsActive(false);
        mUserRepository.save(userEntity);
        mVerificationCodeService.save(userEntity);
        return mUserMapper.toUserProduceDto(userEntity);
    }

    @Override
    public void active(ActiveUserConsumeDto activeUserConsumeDto) {
        UserEntity userEntity = mUserRepository.findByEmail(activeUserConsumeDto.getEmail());
        if (userEntity == null) {
            throw new BadRequestException("user does not exist");
        }
        if (userEntity.getIsActive()) {
            throw new BadRequestException("user activated");
        }
        if (activeUserConsumeDto.getCode() != userEntity.getVerificationCode().getCode()) {
            throw new BadRequestException("wrong verification code");
        }
//        if(LocalDateTime.now())
        userEntity.setIsActive(true);
        mUserRepository.save(userEntity);
    }

    @Override
    public String getEmailFromAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    @Override
    public UserEntity getUserFromAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        return mUserRepository.findByEmail(email);
    }
}
