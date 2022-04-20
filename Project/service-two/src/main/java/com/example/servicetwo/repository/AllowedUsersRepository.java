package com.example.servicetwo.repository;

import com.example.servicetwo.entity.AllowedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AllowedUsersRepository extends JpaRepository<AllowedUsers, UUID> {
    boolean existsByCardId(String cardId);
}
