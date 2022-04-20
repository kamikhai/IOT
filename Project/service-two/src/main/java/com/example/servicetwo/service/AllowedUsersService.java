package com.example.servicetwo.service;

import com.example.servicetwo.repository.AllowedUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowedUsersService {
    private final AllowedUsersRepository allowedUsersRepository;

    public boolean isAllowed(String cardId) {
        return allowedUsersRepository.existsByCardId(cardId);
    }
}
