package com.example.backendhii.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.EditUserConsumeDto;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backendhii.dto.consume.LoginConsumeDto;

import javax.servlet.http.HttpServletRequest;

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

    private final AuthenticationManager authenticationManager;


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
        String email = decodedJWT.getSubject();
        return email;
    }

    @Override
    public UserProduceDto editUser(EditUserConsumeDto editConsumeDto, HttpServletRequest request) {
        UserEntity userEntity = mUserRepository.findByEmail(getEmailFromAccessToken(request));
        if (editConsumeDto.getNewPassword()!=null){
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(),
                        editConsumeDto.getPassword()));
            } catch (Exception e) {
                throw new BadRequestException("incorrect password");
            }
        userEntity.setPassword(mPasswordEncoder.encode(editConsumeDto.getNewPassword()));
        }
        if (editConsumeDto.getFirstName() != null) {
            userEntity.setFirstName(editConsumeDto.getFirstName());
        }
        if (editConsumeDto.getMiddleName() != null) {
            userEntity.setMiddleName(editConsumeDto.getMiddleName());
        }
        if (editConsumeDto.getLastName() != null) {
            userEntity.setLastName(editConsumeDto.getLastName());
        }
        mUserRepository.save(userEntity);
        return mUserMapper.toUserProduceDto(userEntity);
    }
}


