package com.example.avyproject.dto;

import com.example.avyproject.entity.AdminUser;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminUserConverter implements DtoConverter <AdminUser, AdminUserDto>{
    @Override
    public AdminUserDto toDto(AdminUser entity) {
        return null;
    }

    @Override
    public AdminUser toEntity(AdminUserDto dto) {
        return AdminUser.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
//        return null;
    }
}
