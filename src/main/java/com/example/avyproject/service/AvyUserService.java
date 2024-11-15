package com.example.avyproject.service;

import com.example.avyproject.dto.*;
import com.example.avyproject.entity.AvyUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AvyUserService {
    AvyUser getByLogin(String login);
    AvyUser getEntityById(Long avyUserId);
    AvyUser registerUser(AvyUser avyUser);
    AvyUserDto getUserByUsername(String name);
    AvyUserDto createNewAvyUser (AvyUserCreateDto avyUserCreateDto);
    AvyUser updateAvyUser (AvyUserUpdateDto userUpdateDto);
    AvyUserDto getUserDtoByEmail (String email);
    AvyUserLightDto getAvyUserLightDtoByEmail (String email);
    AvyUser getUserByToken (String token);
    List<CourseDto> getCoursesInProgress (String email);
    List<CourseDto> getCoursesCompleted (String email);
    List<CourseDto> getCoursesRecommended (String email);
    List<CourseDto> getAllCoursesByCreator (AvyUser avyUser);
    List<AvyUser> getAllUsers ();
    AvyUserDto updateAvyUserInfo (AvyUserUpdateDto avyUserUpdateDto,AvyUser avyUser);
    AvyUserDto updateAvyUserImage(MultipartFile file, AvyUser avyUser);
    void deleteById (Long userId);
    AvyUser getById (Long userId);
}
