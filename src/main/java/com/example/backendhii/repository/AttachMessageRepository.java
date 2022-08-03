package com.example.backendhii.repository;

import com.example.backendhii.entities.AttachMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachMessageRepository extends JpaRepository<AttachMessageEntity, Long> {
}
