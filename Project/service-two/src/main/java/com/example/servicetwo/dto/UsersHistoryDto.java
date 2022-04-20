package com.example.servicetwo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersHistoryDto {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private LocalDateTime addedAt;
    private String name;
    private String cardId;
    private Boolean allowed;
}
