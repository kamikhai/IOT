package com.example.servicetwo.service;

import com.example.servicetwo.dto.RequestDto;
import com.example.servicetwo.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final AllowedUsersService allowedUsersService;
    private final UsersHistoryService usersHistoryService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send("response", msg);
    }

    @SneakyThrows
    @KafkaListener(topics = "request")
    public void check(String msg) {
        ObjectMapper mapper = new ObjectMapper();
        RequestDto requestDto = mapper.readValue(msg, RequestDto.class);
        if (allowedUsersService.isAllowed(requestDto.getId())) {
            sendMessage(mapper.writeValueAsString(ResponseDto.builder().isAllowed(true).build()));
            usersHistoryService.save(requestDto, true);
        } else {
            sendMessage(mapper.writeValueAsString(ResponseDto.builder().isAllowed(false).build()));
            usersHistoryService.save(requestDto, false);
        }
    }

}
