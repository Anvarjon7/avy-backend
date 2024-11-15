package com.example.avyproject.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
@Table(name = "video_module_item")
public class VideoLesson extends Lesson {
    private String fileName;
    private String fileType;
    private String linkToVideo;
}
