package com.example.servicetwo.controller;

import com.example.servicetwo.dto.UsersHistoryDto;
import com.example.servicetwo.service.UsersHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class UsersHistoryController {

    private final UsersHistoryService usersHistoryService;

    @CrossOrigin
    @GetMapping
    public List<UsersHistoryDto> getUsersHistory() {
        return usersHistoryService.findAll();
    }
}
