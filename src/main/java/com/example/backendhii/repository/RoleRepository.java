package com.example.backendhii.repository;

import com.example.backendhii.entities.RoleEntity;
import com.example.backendhii.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByName(RoleEnum name);
}
