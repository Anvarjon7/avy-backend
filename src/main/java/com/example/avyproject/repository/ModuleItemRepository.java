package com.example.avyproject.repository;

import com.example.avyproject.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleItemRepository extends JpaRepository<Lesson, Long> {

}