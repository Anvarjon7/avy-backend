package com.example.avyproject.service;

import com.example.avyproject.converter.CourseConverter;
import com.example.avyproject.dto.CourseDto;
import com.example.avyproject.dto.CourseFullDto;
import com.example.avyproject.dto.CreateCourseDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Course;
import com.example.avyproject.exceptions.CourseNotFoundException;
import com.example.avyproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseConverter converter;
    @Autowired
    // @Qualifier("imageServiceImpl") // or "localImageService"
    private ImageService imageService;

    @Override
    public Course createCourse(CreateCourseDto createCourseDto, AvyUser creator) {
        String pathToImage = imageService.uploadImage(createCourseDto.getCourseImage());
        Course course = converter.createCourseDtoToCourse(createCourseDto);
        course.setLinkToImage(pathToImage);
        course.setCreator(creator);
        courseRepository.save(course);
        // add to recommended to all USER//
        return courseRepository.save(course);
    }

    @Override
    public CourseFullDto getById(Long courseId) {
        return converter.courseToCourseFullDto(courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found")));
    }

    @Override
    public Course getEntityById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));
    }

    @Override
    public CourseFullDto getByIdAndCreatorId(Long courseId, Long creatorId) {
        CourseFullDto byId = getById(courseId);
        Long adminId = byId.getCreator().getId();
        if (!Objects.equals(creatorId, adminId)) {
            throw new CourseNotFoundException(
                    "Course with id " + courseId + " does not belong to User with id " + creatorId);
        }
        return byId;
    }

    @Override
    public CourseFullDto getFullCourseById(Long courseId) {
        return getById(courseId);
    }

    @Override
    public Course getCourseByIdAndCreatorId(Long courseId, Long creatorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));
        Long adminId = course.getCreator().getId();
        if (!Objects.equals(creatorId, adminId)) {
            throw new CourseNotFoundException(
                    "Course with id " + courseId + " does not belong to User with id " + creatorId);
        }
        return course;
    }

    @Override
    public List<Course> getAllCoursesByCreator(AvyUser avyUser) {
        List<Course> courses = courseRepository.findCoursesByCreator(avyUser);
        return courses;
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }
}
