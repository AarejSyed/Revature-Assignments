package com.demo.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.student.domain.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
}
