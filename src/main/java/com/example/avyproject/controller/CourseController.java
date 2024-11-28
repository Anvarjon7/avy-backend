package com.example.avyproject.controller;

import com.example.avyproject.converter.CourseConverter;
import com.example.avyproject.dto.*;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Course;
import com.example.avyproject.service.AvyUserService;
import com.example.avyproject.service.CourseService;
import com.example.avyproject.service.RecommendedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private AvyUserService avyUserService;
    @Autowired
    private CourseConverter converter;
    @Autowired
    private RecommendedService recommendedService;

    @PostMapping("/create-course")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<CourseDto> create(@ModelAttribute CreateCourseDto createCourseDto,@AuthenticationPrincipal AvyUser avyUser) {
//        AvyUser creator = avyUserService.getUserByToken(authHeader.substring(7));
        Course course = courseService.createCourse(createCourseDto,avyUser,avyUser.getId());
        //add Course to all Users as recommended//
        recommendedService.addCourseToRecommendedToAllUsers(course);
        //add Course to all Users as recommended//
        return ResponseEntity.ok(converter.courseToCourseDto(course));
    }

    @GetMapping("/get-full-course/{courseId}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<CourseDto> getById(@PathVariable Long courseId, @RequestHeader("Authorization") String authHeader) {
        AvyUser creator = avyUserService.getUserByToken(authHeader.substring(7));
        return ResponseEntity.ok(converter.courseFullToCourseDto(courseService.getByIdAndCreatorId(courseId, creator.getId())));
    }

    @PostMapping("/init-all")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> initAllCourseAsRecommended(@RequestHeader("Authorization") String authHeader) {
        AvyUser creator = avyUserService.getUserByToken(authHeader.substring(7));
        //add all Course to all Users as recommended//
        recommendedService.initRecommendedMethod();
        //add all Course to all Users as recommended//
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get-all-courses")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<List<CourseDto>> getById(@RequestHeader("Authorization") String authHeader) {
        AvyUser creator = avyUserService.getUserByToken(authHeader.substring(7));
        return ResponseEntity.ok(avyUserService.getAllCoursesByCreator(creator));
    }
}
