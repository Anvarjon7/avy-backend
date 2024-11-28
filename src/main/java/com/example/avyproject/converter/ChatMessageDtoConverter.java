package com.example.avyproject.converter;

import com.example.avyproject.dto.ChatMessageDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.ChatMessage;
import com.example.avyproject.service.AvyUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageDtoConverter {

        private final ModelMapper mapper;

        @Lazy
        @Autowired
        private AvyUserService userService;

        public ChatMessage dtoToChatMessage(ChatMessageDto chatMessageDto){
                ChatMessage chatMessage = mapper.map(chatMessageDto, ChatMessage.class);
                AvyUser user = userService.getById(chatMessageDto.getUserId());
                chatMessage.setUser(user);
                return chatMessage;
        }

        public ChatMessageDto chatMessageToDto(ChatMessage chatMessage){
                ChatMessageDto dto = mapper.map(chatMessage, ChatMessageDto.class);
                dto.setUserId(chatMessage.getUser().getId());
                return dto;
        }

        public List<ChatMessageDto> chatMessagesToDtos(List<ChatMessage> chatMessages){
                List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
                for (ChatMessage chatMessage : chatMessages){
                        ChatMessageDto dto = mapper.map(chatMessage, ChatMessageDto.class);
                        dto.setUserId(chatMessage.getUser().getId());
                        chatMessageDtos.add(dto);
                }
                return chatMessageDtos;
        }
}
