package com.example.avyproject.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AvyUserLightDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private int coursesInProgress; // int количество
    private int coursesCompleted; // int количество
    private int coursesRecommended; // int количество
    private Integer coins; // Общее количество монет пользователя
    private List<AchievementDto> achievements;
    private List<AwardDto> awards;
    private List<String> roles; //стринги ролей
    private int avatarId;
    private String userJob;
    private String userLinkedIn;
    private String linkToImage;
}
