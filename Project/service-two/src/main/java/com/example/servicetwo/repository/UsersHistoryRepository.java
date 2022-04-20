package com.example.servicetwo.repository;

import com.example.servicetwo.entity.UsersHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersHistoryRepository extends JpaRepository<UsersHistory, UUID> {

}
