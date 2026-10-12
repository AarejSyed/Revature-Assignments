package com.demo.student.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.student.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByLastName(String lastName);
    List<Student> findBySchool_Name(String name);
    Page<Student> findAll(Pageable pageable);
}
