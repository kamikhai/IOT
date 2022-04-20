package com.example.servicetwo.service;

import com.example.servicetwo.dto.RequestDto;
import com.example.servicetwo.dto.UsersHistoryDto;
import com.example.servicetwo.entity.UsersHistory;
import com.example.servicetwo.repository.UsersHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersHistoryService {
    private final UsersHistoryRepository usersHistoryRepository;

    public List<UsersHistoryDto> findAll() {
        return usersHistoryRepository.findAll().stream().map(usersHistory -> UsersHistoryDto.builder()
                .name(usersHistory.getName())
                .addedAt(usersHistory.getAddedAt())
                .cardId(usersHistory.getCardId())
                .allowed(usersHistory.isAllowed()).build()).collect(Collectors.toList());
    }

    public void save(RequestDto requestDto, boolean isAllowed) {
        UsersHistory usersHistory = UsersHistory.builder()
                .name(requestDto.getName())
                .cardId(requestDto.getId())
                .allowed(isAllowed)
                .addedAt(LocalDateTime.now())
                .build();
        usersHistoryRepository.save(usersHistory);
    }
}
