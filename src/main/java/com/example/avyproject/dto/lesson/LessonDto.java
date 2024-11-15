package com.example.avyproject.dto.lesson;

import com.example.avyproject.entity.AvyModule;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class LessonDto {
    private Long id;
    private String title;
    private String itemType;
    private Integer itemOrder;
    private Long moduleId;
}
