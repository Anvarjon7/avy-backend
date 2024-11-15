package com.example.avyproject.repository;

import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
    List<CourseProgress> findByUserIdAndStatus(Long userId, String status);
    List<CourseProgress> findByUserId(Long userId);
    List<CourseProgress> findByCourseId(Long courseId);
    List<CourseProgress> findCourseProgressByCourse_IdAndStatusNot(Long courseId,String status);
    Optional<CourseProgress> findCourseProgressByUserIdAndCourseId(Long userId,Long courseId);
}
