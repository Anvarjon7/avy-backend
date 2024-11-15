package com.example.avyproject.service;

import com.example.avyproject.converter.CourseConverter;
import com.example.avyproject.dto.*;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.CourseProgress;
import com.example.avyproject.entity.Role;
import com.example.avyproject.exceptions.AccountNotFoundException;
import com.example.avyproject.exceptions.RoleNotFoundException;
import com.example.avyproject.repository.AvyUserRepository;
import com.example.avyproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AvyUserServiceImpl implements AvyUserService {

    @Autowired
    AvyUserRepository avyUserRepository;
    @Autowired
    private AvyUserConverter avyUserConverter;
    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseConverter courseConverter;
    @Autowired
    private ImageService imageService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AvyUser getByLogin(String login) {
        return avyUserRepository.findByEmail(login)
                .orElseThrow(() -> new AccountNotFoundException("User with login " + login + " not found"));
    }

    @Override
    public AvyUser getEntityById(Long avyUserId) {
        return avyUserRepository.findById(avyUserId)
                .orElseThrow(() -> new AccountNotFoundException("User with login " + avyUserId + " not found"));
    }


    @Override
    public AvyUser registerUser(AvyUser avyUser) {
        return avyUserRepository.save(avyUser);
    }

    @Override
    public AvyUserDto getUserByUsername(String name) {
        AvyUser avyUser = avyUserRepository.findByFirstName(name)
                .orElseThrow(() -> new AccountNotFoundException("User with login " + name + " not found"));
        return avyUserConverter.avyUserToAvyUserDto(avyUser);
    }

    @Override
    public AvyUserDto createNewAvyUser(AvyUserCreateDto avyUserCreateDto) {
        Role userRole = roleRepository.findRoleByRoleName(avyUserCreateDto.getRoleName())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        AvyUser newUser = AvyUser.builder()
                .email(avyUserCreateDto.getEmail())
                .password(avyUserCreateDto.getPassword())
                .firstName(avyUserCreateDto.getFirstName())
                .lastName(avyUserCreateDto.getLastName())
                .userName(avyUserCreateDto.getUsername())
                .creationDate(LocalDate.now())
                .roles(Set.of(userRole))
                .build();
        AvyUser save = avyUserRepository.save(newUser);
        //add all courses to user as recommended
        return getUserDtoByEmail(save.getEmail());
    }

    @Override
    public AvyUser updateAvyUser(AvyUserUpdateDto userUpdateDto) {
        AvyUser avyUser = getEntityById(userUpdateDto.getId());
//        String pathToImage = imageService.uploadImage(userUpdateDto.getUserImage());
        avyUser.setFirstName(userUpdateDto.getFirstName());
        avyUser.setLastName(userUpdateDto.getLastName());
        avyUser.setUserName(userUpdateDto.getUserName());
        avyUser.setAvatarId(userUpdateDto.getAvatarId());
        avyUser.setUserJob(userUpdateDto.getUserJob());
        avyUser.setUserLinkedIn(userUpdateDto.getUserLinkedIn());
//        avyUser.setLinkToImage(pathToImage);
        return avyUserRepository.save(avyUser);
    }

    @Override
    @Transactional
    public void deleteById (Long userId){
        List<CourseProgress> allCourseProgressByUserId = courseProgressService.getAllCourseProgressByUserId(userId);
        courseProgressService.deleteListCourseProgress(allCourseProgressByUserId);
        avyUserRepository.deleteById(userId);
    }

    @Override
    public AvyUserDto getUserDtoByEmail(String email) {
//        AvyUser byLogin = getByLogin(email);
//        return avyUserConverter.avyUserToAvyUserDto(byLogin);
        AvyUser avyUser = avyUserRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("User with login " + email + " not found"));
        return avyUserConverter.avyUserToAvyUserDto(avyUser);
    }

    @Override
    public AvyUserLightDto getAvyUserLightDtoByEmail(String email) {
        AvyUser avyUser = avyUserRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("User with login " + email + " not found"));
        return avyUserConverter.avyUserToAvyUserLightDto(avyUser);
    }

    public AvyUser getUserByToken (String token){
        String login = jwtService.getUsernameFromToken(token);
       return getByLogin(login);
    }

    @Override
    public List<CourseDto> getCoursesInProgress (String email){
        Long userId = getUserIdByLogin(email);
        return courseProgressService.getCoursesInProgress(userId)
                .stream()
                .map(CourseProgress::getCourse)
                .map(courseConverter::courseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesCompleted (String email){
        Long userId = getUserIdByLogin(email);
        return courseProgressService.getCoursesCompleted(userId)
                .stream()
                .map(CourseProgress::getCourse)
                .map(courseConverter::courseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesRecommended (String email){
        Long userId = getUserIdByLogin(email);
        return courseProgressService.getCoursesRecommended(userId)
                .stream()
                .map(CourseProgress::getCourse)
                .map(courseConverter::courseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCoursesByCreator (AvyUser avyUser){
        return courseService.getAllCoursesByCreator(avyUser)
                .stream()
                .map(courseConverter::courseToCourseDto)
                .collect(Collectors.toList());
    }


    private Long getUserIdByLogin(String email) {
        AvyUser avyUser = avyUserRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("User with login " + email + " not found"));
        return avyUser.getId();
    }

    @Override
    public AvyUserDto updateAvyUserInfo (AvyUserUpdateDto avyUserUpdateDto,AvyUser avyUser){
        if (!Objects.equals(avyUser.getId(), avyUserUpdateDto.getId())){
            throw new AccountNotFoundException("Account with id "+avyUser.getId()+" trying to change different account info with id "+avyUserUpdateDto.getId());
        }
        return avyUserConverter.avyUserToAvyUserDto(updateAvyUser(avyUserUpdateDto));
    }

    @Override
    public AvyUserDto updateAvyUserImage(MultipartFile file, AvyUser avyUser) {
        String pathToImage = imageService.uploadImage(file);
        avyUser.setLinkToImage(pathToImage);
        return avyUserConverter.avyUserToAvyUserDto(avyUserRepository.save(avyUser));
    }

    @Override
    public List<AvyUser> getAllUsers() {
        return avyUserRepository.findAll();
    }

    @Override
    public AvyUser getById(Long userId) {
        return avyUserRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException("User with id " + userId + " not found"));
    }
}
