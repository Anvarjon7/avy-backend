package com.example.avyproject.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AvyUser user;

    private String type;
    private String description;
    private LocalDate dateEarned;
}