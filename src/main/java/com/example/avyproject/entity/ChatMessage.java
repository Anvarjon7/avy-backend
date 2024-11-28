package com.example.avyproject.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AvyUser user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String messageType;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}