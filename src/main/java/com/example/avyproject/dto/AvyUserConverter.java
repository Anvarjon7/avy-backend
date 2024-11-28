package com.example.avyproject.dto;

import com.example.avyproject.converter.CourseProgressDtoConverter;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Role;
import com.example.avyproject.entity.UserProgress;
import com.example.avyproject.service.AchievementService;
import com.example.avyproject.service.AwardService;
import com.example.avyproject.service.CourseProgressService;
import com.example.avyproject.service.UserProgressService;
import com.example.avyproject.service.utility.RelativePathConverter;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@Component
@RequiredArgsConstructor
public class AvyUserConverter {

    private final CourseProgressService courseProgressService;
    private final CourseProgressDtoConverter courseProgressDtoConverter;
    private final AchievementService achievementService;
    private final AwardService awardService;
    private final UserProgressService userProgressService;

    public AvyUserDto avyUserToAvyUserDto (AvyUser avyUser){
        AvyUserDto avyUserDto = new AvyUserDto();
        avyUserDto.setId(avyUser.getId());
        avyUserDto.setFirstName(avyUser.getFirstName());
        avyUserDto.setLastName(avyUser.getLastName());
        avyUserDto.setEmail(avyUser.getEmail());
        avyUserDto.setUserName(avyUser.getUserName());
        avyUserDto.setLinkToAvatar(avyUser.getLinkToAvatar());
        avyUserDto.setLinkToImage(RelativePathConverter.getRelativePath(avyUser.getLinkToImage()));
        avyUserDto.setUserJob(avyUser.getUserJob());
        avyUserDto.setUserLinkedIn(avyUser.getUserLinkedIn());
        avyUserDto.setAvatarId(avyUser.getAvatarId());
        avyUserDto.setCreationDate(avyUser.getCreationDate());

        avyUserDto.setCoursesInProgress(courseProgressService.getCoursesInProgress(avyUser.getId())
                        .stream()
                        .map(courseProgress -> courseProgressDtoConverter.courseProgressToDto(courseProgress))
                        .collect(Collectors.toList())
        );
        avyUserDto.setCoursesCompleted(courseProgressService.getCoursesCompleted(avyUser.getId())
                .stream()
                .map(courseProgress -> courseProgressDtoConverter.courseProgressToDto(courseProgress))
                .collect(Collectors.toList())
        );
        avyUserDto.setCoursesRecommended(courseProgressService.getCoursesRecommended(avyUser.getId())
                .stream()
                .map(courseProgress -> courseProgressDtoConverter.courseProgressToDto(courseProgress))
                .collect(Collectors.toList())
        );


        List<UserProgress> listUserProgress = userProgressService.getAllUserProgressByUserId(avyUserDto.getId());
        if (listUserProgress.size()>0){
            UserProgress userProgress = listUserProgress.get(0);
            int userCoins = userProgress.getCoins();
            avyUserDto.setCoins(userCoins);
        }

        avyUserDto.setAchievements(achievementService.getAchievementsByUserId(avyUserDto.getId()));
        avyUserDto.setAwards(awardService.getAwardByUserId(avyUserDto.getId()));

        return avyUserDto;
    }

    public AvyUserLightDto avyUserToAvyUserLightDto (AvyUser avyUser){
        AvyUserLightDto avyUserLightDto = new AvyUserLightDto();
        avyUserLightDto.setId(avyUser.getId());
        avyUserLightDto.setFirstName(avyUser.getFirstName());
        avyUserLightDto.setLastName(avyUser.getLastName());
        avyUserLightDto.setUserName(avyUser.getUserName());
        avyUserLightDto.setEmail(avyUser.getEmail());
        avyUserLightDto.setCoursesInProgress(courseProgressService.getCoursesInProgress(avyUser.getId()).size());
        avyUserLightDto.setCoursesCompleted(courseProgressService.getCoursesCompleted(avyUser.getId()).size());
        avyUserLightDto.setCoursesRecommended(courseProgressService.getCoursesRecommended(avyUser.getId()).size());
        List<UserProgress> listUserProgress = userProgressService.getAllUserProgressByUserId(avyUser.getId());
        if (listUserProgress.size()>0){
            UserProgress userProgress = listUserProgress.get(0);
            int userCoins = userProgress.getCoins();
            avyUserLightDto.setCoins(userCoins);
        }

        avyUserLightDto.setAchievements(achievementService.getAchievementsByUserId(avyUser.getId()));
        avyUserLightDto.setAwards(awardService.getAwardByUserId(avyUser.getId()));

        Set<Role> roles = avyUser.getRoles();
        avyUserLightDto.setRoles(roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList())
        );
        avyUserLightDto.setAvatarId(avyUser.getAvatarId());
        avyUserLightDto.setUserJob(avyUser.getUserJob());
        avyUserLightDto.setUserLinkedIn(avyUser.getUserLinkedIn());
        avyUserLightDto.setLinkToImage(RelativePathConverter.getRelativePath(avyUser.getLinkToImage()));
        return avyUserLightDto;
    }

    public AvyUser avyUserDtoToEntity(AvyUserDto avyUserDto){
        return AvyUser.builder()
                .firstName(avyUserDto.getFirstName())
                .lastName(avyUserDto.getLastName())
                .email(avyUserDto.getEmail())
                .userName(avyUserDto.getUserName())
                .linkToAvatar(avyUserDto.getLinkToAvatar())
                .linkToImage(avyUserDto.getLinkToImage())
                .creationDate(avyUserDto.getCreationDate())
                .avatarId(avyUserDto.getAvatarId())
                .userJob(avyUserDto.getUserJob())
                .userLinkedIn(avyUserDto.getUserLinkedIn())
                .build();
    }
}
