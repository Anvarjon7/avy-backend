package com.example.avyproject.controller;

import com.example.avyproject.dto.lesson.*;
import com.example.avyproject.entity.Lesson;
import com.example.avyproject.service.LessonService;
import com.example.avyproject.service.strategy.VideoLessonStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/lesson")
public class LessonController {

    @Autowired
    VideoLessonStrategy videoLessonStrategy;

    @Autowired
    LessonContentMapper lessonContentMapper;

    @Autowired
    LessonService lessonService;


    @PostMapping("/create-lesson")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createLesson(HttpServletRequest request) {
        CreateLessonDto createLessonDto = lessonContentMapper.mapToCreateLessonDto(request);
        Lesson lesson = lessonService.create(createLessonDto);
        return ResponseEntity.ok(lessonService.toDto(lesson));
        //
    }
}
