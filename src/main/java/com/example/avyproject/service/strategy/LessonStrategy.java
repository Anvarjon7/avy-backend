package com.example.avyproject.service.strategy;

import com.example.avyproject.dto.lesson.CreateLessonDto;
import com.example.avyproject.dto.lesson.LessonDto;
import com.example.avyproject.entity.Lesson;
import org.springframework.web.multipart.MultipartFile;

public interface LessonStrategy {
    boolean supports(String lessonType);
    boolean requiredFile();
    CreateLessonDto processFile (CreateLessonDto createLessonDto, MultipartFile file);
    String getLessonType ();
    Lesson createLesson(CreateLessonDto createLessonDto);
    Class<? extends CreateLessonDto> getSupportedDtoClass();
    LessonDto covertToDto (Lesson lesson);
}
