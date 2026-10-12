package com.demo.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.student.domain.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {
}
