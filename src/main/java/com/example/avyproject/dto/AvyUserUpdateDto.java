package com.example.avyproject.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AvyUserUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private int avatarId;
    private String userJob;
    private String userLinkedIn;
//    private MultipartFile userImage;
}
