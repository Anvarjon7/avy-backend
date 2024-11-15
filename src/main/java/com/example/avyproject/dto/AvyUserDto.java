package com.example.avyproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AvyUserDto {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String userName;
        private String linkToAvatar;
        private String linkToImage;
        private LocalDate creationDate; // created?
        private List<CourseProgressDto> coursesInProgress;
        private List<CourseProgressDto> coursesCompleted;
        private List<CourseProgressDto> coursesRecommended;
        private Integer coins; // Общее количество монет пользователя
        private List<AchievementDto> achievements;
        private List<AwardDto> awards;
        private int avatarId;
        private String userJob;
        private String userLinkedIn;
}
