package com.example.backendhii.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.EditUserConsumeDto;
import com.example.backendhii.dto.consume.LoginConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.entities.VerificationCodeEntity;
import com.example.backendhii.enums.RoleEnum;
import com.example.backendhii.exceptions.BadRequestException;
import com.example.backendhii.mapper.UserMapper;
import com.example.backendhii.repository.RoleRepository;
import com.example.backendhii.repository.UserRepository;
import com.example.backendhii.repository.VerificationCodeRepository;
import com.example.backendhii.services.UserService;
import com.example.backendhii.services.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    private final HttpServletRequest request;

    private final VerificationCodeRepository mVerificationCodeRepository;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Value("${URL_AVATAR}")
    private String URL_AVATAR;

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
    public String getEmailFromAccessToken() {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        return email;
    }

    @Override
    public UserProduceDto editUser(EditUserConsumeDto editConsumeDto) {
        UserEntity userEntity = mUserRepository.findByEmail(getEmailFromAccessToken());
        if (editConsumeDto.getNewPassword() != null) {
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

    @Override
    public UserProduceDto uploadImage(MultipartFile multipartFile) throws IOException {
        UserEntity userEntity = mUserRepository.findByEmail(getEmailFromAccessToken());
        String fileName = multipartFile.getOriginalFilename();
        int position = fileName.lastIndexOf(".");
        String s = fileName.substring(position + 1);
        if (!s.equalsIgnoreCase("JPG") && !s.equalsIgnoreCase("PNG")) {
            throw new BadRequestException("Incorrect file format");
        }
        String uploadDir = URL_AVATAR;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(userEntity.getId() + ".PNG");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {
            throw new BadRequestException("Update image fail");
        }
        userEntity.setAvatar(URL_AVATAR + "/" + userEntity.getId() + ".PNG");
        mUserRepository.save(userEntity);
        return mUserMapper.toUserProduceDto(userEntity);
    }

    @Override
    public void getCodePassword(LoginConsumeDto loginConsumeDto) throws MessagingException {
        UserEntity userEntity = mUserRepository.findByEmail(loginConsumeDto.getEmail());
        if (userEntity == null) {
            throw new BadRequestException("mail address does not exist");
        }
        String random = RandomStringUtils.random(6, "1234567890");
        if (userEntity.getVerificationCode().getCode()==null) {
            VerificationCodeEntity verificationCodeEntity = VerificationCodeEntity.builder()
                    .code(Integer.parseInt(random))
                    .user(userEntity)
                    .build();
            mVerificationCodeRepository.save(verificationCodeEntity);
            mVerificationCodeService.sendEmailContainVerificationCode(userEntity.getEmail(), Integer.parseInt(random));
        }
        if(userEntity.getVerificationCode().getCode()!=null){
        }
    }

    @Override
    public UserProduceDto resetPassword(UserConsumeDto userConsumeDto) {
        UserEntity userEntity=mUserRepository.findByEmail(userConsumeDto.getEmail());

        if(userEntity==null){
            throw new BadRequestException("Email is incorrect");
        }
        if (userConsumeDto.getCode() != userEntity.getVerificationCode().getCode()) {
            throw new BadRequestException("wrong verification code");
        }
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        mUserRepository.save(userEntity);
        return mUserMapper.toUserProduceDto(userEntity);
    }
}