package com.example.backendhii.services.impl;

import com.example.backendhii.entities.RoleEntity;
import com.example.backendhii.repository.RoleRepository;
import com.example.backendhii.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository mRoleRepository;

    public void create(RoleEntity roleEntity) {
        mRoleRepository.save(roleEntity);
    }
}
