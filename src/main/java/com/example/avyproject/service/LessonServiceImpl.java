package com.example.avyproject.service;

import com.example.avyproject.dto.lesson.CreateLessonDto;
import com.example.avyproject.dto.lesson.LessonDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Course;
import com.example.avyproject.entity.CourseProgress;
import com.example.avyproject.entity.Lesson;
import com.example.avyproject.exceptions.LessonProgressNotFoundException;
import com.example.avyproject.repository.LessonRepository;
import com.example.avyproject.service.strategy.LessonHandler;
import com.example.avyproject.service.strategy.LessonStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonHandler lessonHandler;
    @Autowired
    LessonRepository lessonRepository;

    private final VideoService videoService;

    public LessonServiceImpl(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public Lesson create(CreateLessonDto createLessonDto) {
        LessonStrategy lessonStrategy = lessonHandler.getStrategy(createLessonDto.getItemType());
        return lessonStrategy.createLesson(createLessonDto);
    }

    @Override
    public LessonDto toDto(Lesson lesson) {
        LessonStrategy lessonStrategy = lessonHandler.getStrategy(lesson.getItemType());
        return lessonStrategy.covertToDto(lesson);
    }

    @Override
    public LessonDto getDtoById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(()-> new LessonProgressNotFoundException("Lesson not found"));
        return toDto(lesson);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(()-> new LessonProgressNotFoundException("Lesson not found"));
    }

    @Override
    public LessonDto update(LessonDto lessonDto) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return null;
    }

}
