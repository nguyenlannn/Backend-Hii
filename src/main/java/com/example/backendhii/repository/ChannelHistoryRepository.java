package com.example.backendhii.repository;

import com.example.backendhii.entities.ChannelHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelHistoryRepository extends JpaRepository<ChannelHistoryEntity, Long> {
}
