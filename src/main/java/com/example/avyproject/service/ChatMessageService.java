package com.example.avyproject.service;

import com.example.avyproject.dto.ChatMessageDto;

import java.util.List;

public interface ChatMessageService {

    ChatMessageDto createChatMessage(ChatMessageDto chatMessageDto);
    List<ChatMessageDto> getAllMessages();
}
