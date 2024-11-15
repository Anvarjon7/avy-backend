package com.example.avyproject.controller;

import com.example.avyproject.converter.AvyModuleConverter;
import com.example.avyproject.dto.AvyModuleDto;
import com.example.avyproject.dto.CourseDto;
import com.example.avyproject.dto.CreateAvyModuleDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.service.AvyModuleService;
import com.example.avyproject.service.AvyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/module")
public class AvyModuleController {

    @Autowired
    private AvyUserService avyUserService;
    @Autowired
    private AvyModuleService moduleService;
    @Autowired
    private AvyModuleConverter converter;
    @PostMapping("/create-module")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<AvyModuleDto> create(@ModelAttribute CreateAvyModuleDto createModuleDto, @RequestHeader("Authorization") String authHeader){
        AvyUser creator = avyUserService.getUserByToken(authHeader.substring(7));
        return ResponseEntity.ok(converter.moduleToModuleDto(moduleService.createModule(createModuleDto,creator)));
    }
}