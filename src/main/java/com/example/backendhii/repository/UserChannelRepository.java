package com.example.backendhii.repository;

import com.example.backendhii.entities.UserChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannelEntity, Long> {
}
