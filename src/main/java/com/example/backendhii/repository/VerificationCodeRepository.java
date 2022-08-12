package com.example.backendhii.repository;

import com.example.backendhii.entities.VerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, String> {

    VerificationCodeEntity findByCode(Integer code);

    VerificationCodeEntity findByUser(UserRepository user);
}
