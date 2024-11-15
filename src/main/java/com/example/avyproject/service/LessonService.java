package com.example.avyproject.service;

import com.example.avyproject.dto.lesson.CreateLessonDto;
import com.example.avyproject.dto.lesson.LessonDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.CourseProgress;
import com.example.avyproject.entity.Lesson;
import org.springframework.stereotype.Service;

@Service
public interface LessonService {

    Lesson create(CreateLessonDto createLessonDto);
    LessonDto getDtoById(Long id);
    Lesson getById(Long id);
    LessonDto update(LessonDto lessonDto);
    String delete(Long id);
    LessonDto toDto (Lesson lesson);
}
