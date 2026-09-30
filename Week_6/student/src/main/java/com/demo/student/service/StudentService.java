package com.demo.student.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.student.dao.StudentDao;
import com.demo.student.domain.Student;
import com.demo.student.exceptions.StudentNotFoundException;

@Service
public class StudentService {
    private final StudentDao studentDao;
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    public StudentService(@Qualifier("entityManagerStudentDao") StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Transactional
    public Student insertStudent(Student student) {
        return studentDao.insert(student);
    }

    @Transactional(readOnly=true)
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }

    @Transactional(readOnly=true)
    public Student getStudentById(int id) {
        Student student = studentDao.findById(id);

        if (student == null) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        
        return student;
    }

    @Transactional
    public void deleteStudent(int id) {
        Student student = getStudentById(id);
        studentDao.delete(student);
    }

    @Transactional(readOnly=true)
    public List<Student> getStudentsByLastName(String lastName) {
        return studentDao.findByLastName(lastName);
    }

    // We don't need call to DAO here.
    // This is in a transaction and existingStudent is a
    // managed entity, so any changes to it will be tracked.
    // Before the transaction closes, dirty checking will occur.
    // This will detect the change to the managed entity,
    // and the changes will be auto committed.
    @Transactional
    public Student updateStudent(int id, Student student) {
        Student existingStudent = getStudentById(id);

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        return existingStudent;
    }
}
