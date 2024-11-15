package com.example.avyproject.converter;

import com.example.avyproject.dto.*;
import com.example.avyproject.entity.Course;
import com.example.avyproject.service.CourseProgressService;
import com.example.avyproject.service.utility.RelativePathConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConverter {

    @Autowired
    AvyModuleConverter avyModuleConverter;
    @Autowired
    CourseProgressService courseProgressService;
    @Autowired
    AvyUserConverter avyUserConverter;

    public Course createCourseDtoToCourse(CreateCourseDto createCourseDto) {

        Course build = Course.builder()
                .title(createCourseDto.getTitle())
                .description(createCourseDto.getDescription())
                .category(createCourseDto.getCategory())
                .level(createCourseDto.getLevel())
                .creationDate(LocalDate.now())
                .build();

        return build;
    }

    public CourseDto courseToCourseDto(Course course) {
        String shortLinkToImage = RelativePathConverter.getRelativePath(course.getLinkToImage());
        List<AvyModuleDto> modules = new ArrayList<>();
        if (course.getAvyModuleList()!=null) {
            modules = course.getAvyModuleList()
                    .stream()
                    .map(avyModuleConverter::moduleToModuleDto)
                    .collect(Collectors.toList());
        }

        return CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .courseImage(shortLinkToImage)
                .category(course.getCategory())
                .level(course.getLevel())
                .status(course.getStatus())
                .creationDate(course.getCreationDate())
                .lastUpdateDate(course.getLastUpdateDate())
                .modules(modules)
                .participants(courseProgressService.getAllUserByCourse(course.getId()))
                .build();
    }

    public CourseFullDto courseToCourseFullDto(Course course) {
        String shortLinkToImage = RelativePathConverter.getRelativePath(course.getLinkToImage());
        return CourseFullDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .courseImage(shortLinkToImage)
                .category(course.getCategory())
                .level(course.getLevel())
                .creator(avyUserConverter.avyUserToAvyUserLightDto(course.getCreator()))
                .status(course.getStatus())
                .creationDate(course.getCreationDate())
                .lastUpdateDate(course.getLastUpdateDate())
                .modules(course.getAvyModuleList()
                        .stream()
                        .map(avyModuleConverter::moduleToModuleDto)
                        .collect(Collectors.toList()))
                .participants(courseProgressService.getAllUserByCourse(course.getId()))
                .build();
    }

    public CourseDto courseFullToCourseDto(CourseFullDto courseFullDto) {
        return CourseDto.builder()
                .id(courseFullDto.getId())
                .title(courseFullDto.getTitle())
                .description(courseFullDto.getDescription())
                .courseImage(courseFullDto.getCourseImage())
                .category(courseFullDto.getCategory())
                .level(courseFullDto.getLevel())
                .status(courseFullDto.getStatus())
                .creationDate(courseFullDto.getCreationDate())
                .lastUpdateDate(courseFullDto.getLastUpdateDate())
                .modules(courseFullDto.getModules())
                .participants(courseFullDto.getParticipants())
                .build();
    }
}

