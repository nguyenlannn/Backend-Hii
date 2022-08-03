package com.example.backendhii.services.impl;

import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.enums.RoleEnum;
import com.example.backendhii.exceptions.BadRequestException;
import com.example.backendhii.mapper.RoleMapper;
import com.example.backendhii.mapper.UserMapper;
import com.example.backendhii.repository.RoleRepository;
import com.example.backendhii.repository.UserRepository;
import com.example.backendhii.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;
    private final RoleRepository mRoleRepository;
    private final PasswordEncoder mPasswordEncoder;

    private final UserMapper mUserMapper;

    private final RoleMapper mRoleMapper;

    public void createAdmin(UserEntity userEntity) {
        userEntity.setRoles(mRoleRepository.findAll());
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        mUserRepository.save(userEntity);
    }

    public void createUser(UserEntity userEntity) {
        userEntity.setRoles(mRoleRepository.findByName(RoleEnum.ROLE_USER));
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        mUserRepository.save(userEntity);
    }

    @Override
    public UserProduceDto register(UserConsumeDto userConsumeDto) {
        UserEntity userEntity = userConsumeDto.toUserEntity();

        if (mUserRepository.existsByEmail(userConsumeDto.getEmail())) {
            throw new BadRequestException("Email already exist");
        }
        userEntity.setRoles(mRoleRepository.findByName(RoleEnum.ROLE_USER));
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        mUserRepository.save(userEntity);

        UserProduceDto userProduceDto = mUserMapper.toUserProduceDto(userEntity);

        userProduceDto.setRoles(userEntity.getRoles().stream()
                .map(mRoleMapper::toRoleProduceDto).collect(Collectors.toList()));
        return userProduceDto;
    }
}
