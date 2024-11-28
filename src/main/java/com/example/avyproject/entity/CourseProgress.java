package com.example.avyproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.jca.support.LocalConnectionFactoryBean;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AvyUser user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private int progress;
    private String status;
    private LocalDate lastAccessed;
    @Column(columnDefinition = "boolean default false")
    private boolean exited;
    private LocalDate lastExited;
    @JsonManagedReference
    @OneToMany(mappedBy = "courseProgress")
    private List<LessonProgress> lessonProgresses;
}
