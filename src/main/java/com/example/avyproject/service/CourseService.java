package com.example.avyproject.service;

import com.example.avyproject.dto.CourseDto;
import com.example.avyproject.dto.CourseFullDto;
import com.example.avyproject.dto.CreateCourseDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Course;

import java.util.List;

public interface CourseService {

    Course createCourse (CreateCourseDto createCourseDto, AvyUser creator);
    CourseFullDto getById (Long courseId);
    public Course getEntityById(Long courseId);
    CourseFullDto getByIdAndCreatorId(Long courseId,Long creatorId);
    Course getCourseByIdAndCreatorId(Long courseId,Long creatorId);
    List <Course> getAllCoursesByCreator(AvyUser avyUser);
    List<Course> getAll ();
    CourseFullDto getFullCourseById(Long courseId);
}
