package com.example.avyproject.controller;

import com.example.avyproject.converter.CourseProgressDtoConverter;
import com.example.avyproject.dto.*;
import com.example.avyproject.dto.lesson.LessonDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Course;
import com.example.avyproject.entity.CourseProgress;
import com.example.avyproject.entity.Lesson;
import com.example.avyproject.service.AvyUserService;
import com.example.avyproject.service.CourseProgressService;
import com.example.avyproject.service.CourseService;
import com.example.avyproject.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class AvyUserManagementController {

    @Autowired
    private AvyUserService avyUserService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CourseProgressDtoConverter courseProgressDtoConverter;

    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvyUserDto> getUserByUsername(@PathVariable String username) {
        AvyUserDto userDto = avyUserService.getUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvyUserDto> getCurrentUser(Authentication authentication) {
        String email = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof AvyUser) {
            AvyUser avyUser = (AvyUser) principal;
            email = avyUser.getEmail();
        }
        AvyUserDto userDto = avyUserService.getUserDtoByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/info/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvyUserDto> updateUserInfo(@RequestBody AvyUserUpdateDto avyUserUpdateDto,
            @RequestHeader("Authorization") String authHeader) {
        AvyUser avyUser = avyUserService.getUserByToken(authHeader.substring(7));
        AvyUserDto avyUserDto = avyUserService.updateAvyUserInfo(avyUserUpdateDto, avyUser);
        return ResponseEntity.ok(avyUserDto);
    }

    @PostMapping("/info/updateImage")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvyUserDto> updateUserInfo(@RequestParam("userImage") MultipartFile file,
            @RequestHeader("Authorization") String authHeader) {
        AvyUser avyUser = avyUserService.getUserByToken(authHeader.substring(7));
        AvyUserDto avyUserDto = avyUserService.updateAvyUserImage(file, avyUser);
        return ResponseEntity.ok(avyUserDto);
    }

    @GetMapping("/light-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvyUserLightDto> getCurrentUserDetail(Authentication authentication) {
        String email = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof AvyUser avyUser) {
            email = avyUser.getEmail();
        }
        AvyUserLightDto byLogin = avyUserService.getAvyUserLightDtoByEmail(email);
        return ResponseEntity.ok(byLogin);
    }

    @GetMapping("/course/progressing")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CourseDto>> getCourseInProgress(Authentication authentication) {
        String email = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof AvyUser avyUser) {
            email = avyUser.getEmail();
        }
        return ResponseEntity.ok(avyUserService.getCoursesInProgress(email));
    }

    @GetMapping("/course/completed")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CourseDto>> getCourseCompleted(Authentication authentication) {
        String email = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof AvyUser avyUser) {
            email = avyUser.getEmail();
        }
        return ResponseEntity.ok(avyUserService.getCoursesCompleted(email));
    }

    @GetMapping("/course/recommended")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CourseDto>> getCourseRecommended(Authentication authentication) {
        String email = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof AvyUser avyUser) {
            email = avyUser.getEmail();
        }
        return ResponseEntity.ok(avyUserService.getCoursesRecommended(email));
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserByUsername(@PathVariable Long userId) {
        avyUserService.deleteById(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/course/start/{courseId}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<CourseProgressFullDto> signUpToCourse(@PathVariable Long courseId,
            @RequestHeader("Authorization") String authHeader) {
        AvyUser user = avyUserService.getUserByToken(authHeader.substring(7));
        Course course = courseService.getEntityById(courseId);
        return ResponseEntity.ok(courseProgressService.startCourseByUser(course, user));
    }

    @GetMapping("/start-lesson/{lessonId}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<LessonDto> getLessonById(@PathVariable Long lessonId,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(lessonService.getDtoById(lessonId));
    }

    @PostMapping("/complete-lesson/{lessonId}")
    @PreAuthorize("isAuthenticated()")
    // ПРАВИМ ЗАПИСИ В ТАБЛИЦЕ ЛЕССОН ПРОГРЕСС ПО ЮЗЕРУ И КУРСУ
    ResponseEntity<CourseProgressDto> completeLesson(@PathVariable Long lessonId,
            @RequestHeader("Authorization") String authHeader) {
        AvyUser user = avyUserService.getUserByToken(authHeader.substring(7));
        Lesson lesson = lessonService.getById(lessonId);
        log.info("ENDPOINT: complete lesson with id " + lesson.getId());
        CourseProgress courseProgress = courseProgressService.completeLesson(lesson, user);
        // return ResponseEntity.ok(converter.convertToDTO(courseProgress));
        return ResponseEntity.ok(courseProgressDtoConverter.courseProgressToDto(courseProgress));
    }

}
