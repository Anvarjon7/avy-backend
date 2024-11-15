package com.example.avyproject.controller;

import com.example.avyproject.dto.AdminUserDto;
import com.example.avyproject.dto.DtoConverter;
import com.example.avyproject.entity.AdminUser;
import com.example.avyproject.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Admin controller",
        description = "Provides CRUD operations to admin")
@RestController
@RequestMapping("api/admins")
@RequiredArgsConstructor
public class AdminUserController {

    private final PasswordEncoder passwordEncoder;
    private final AdminUserService adminUserService;
    private final DtoConverter<AdminUser, AdminUserDto> dtoConverter;

    @Operation(summary = "List of administrators",
            description = "Get a list of all administrators")
    @SecurityRequirement(name = "Authorization")
    @GetMapping
    List<AdminUserDto> getAll() {
        return adminUserService.getAll().stream()
                .map(dtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get by ID",
            description = "Get an administrator by ID")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{id}")
    AdminUserDto getById(@PathVariable("id")
                         @Parameter(description = "Administrator`s ID")Long id) {
        return dtoConverter.toDto(adminUserService.findById(id));
    }

    @Operation(summary = "Add a new administrator",
            description = "Adds a new administrator into the database")
    @PostMapping
    ResponseEntity<AdminUserDto> create(@RequestBody AdminUserDto adminUser){
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        return ResponseEntity.ok(dtoConverter.toDto(adminUserService
                .save(dtoConverter.toEntity(adminUser))));
    }

    @Operation(summary = "Remove an administrator",
            description = "Removes administrator from the database")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping
    public void remove(){
        adminUserService.delete();
    }
}
