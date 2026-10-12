package com.demo.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.student.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
