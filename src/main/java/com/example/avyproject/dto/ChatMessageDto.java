package com.example.avyproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatMessageDto {

    private Long id;
    private Long userId;
    private String content;
    private String messageType;
    private LocalDateTime timestamp;
}
