package com.example.avyproject.service;

import com.example.avyproject.converter.ChatMessageDtoConverter;
import com.example.avyproject.dto.ChatMessageDto;
import com.example.avyproject.entity.ChatMessage;
import com.example.avyproject.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageDtoConverter chatMessageDtoConverter;

    @Override
    public ChatMessageDto createChatMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = chatMessageDtoConverter.dtoToChatMessage(chatMessageDto);
        chatMessage = chatMessageRepository.save(chatMessage);

        return chatMessageDtoConverter.chatMessageToDto(chatMessage);
    }

    @Override
    public List<ChatMessageDto> getAllMessages() {
        List<ChatMessage> chatMessages = chatMessageRepository.findAll();
        return chatMessageDtoConverter.chatMessagesToDtos(chatMessages);
    }
}
