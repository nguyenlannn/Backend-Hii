package com.example.backendhii.repository;

import com.example.backendhii.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Boolean existsByUserAgentAndAccessToken(String userAgent, String accessToken);
}
