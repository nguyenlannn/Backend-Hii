package com.example.backendhii.repository;

import com.example.backendhii.entities.VerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Long> {
}
