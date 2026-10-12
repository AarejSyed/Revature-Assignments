package com.demo.student.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.student.dao.CourseRepository;
import com.demo.student.dao.EnrollmentRepository;
import com.demo.student.dao.SchoolRepository;
import com.demo.student.dao.StudentDao;
import com.demo.student.dao.StudentRepository;
import com.demo.student.domain.School;
import com.demo.student.domain.Student;
import com.demo.student.exceptions.RecordNotFoundException;
import com.demo.student.dto.StudentWriteDto;

@Service
public class StudentService {
    // private final StudentDao studentDao;
    private final StudentRepository studentRepo;
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private EnrollmentRepository enrollmentRepo;
    private CourseRepository courseRepo;
    private SchoolRepository schoolRepo;

    public StudentService(
        StudentRepository studentRepository,
        EnrollmentRepository enrollmentRepository,
        CourseRepository courseRepository,
        SchoolRepository schoolRepository
    ) {
        // this.studentDao = studentDao;
        this.studentRepo = studentRepository;
        this.enrollmentRepo = enrollmentRepository;
        this.courseRepo = courseRepository;
        this.schoolRepo = schoolRepository;
    }

    // STUDENTS

    @Transactional
    public Student insertStudent(StudentWriteDto student) {
        School school = schoolRepo.findById(student.getSchoolId())
            .orElse(null);

        Student newStudent = new Student(
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            school
        );
        return studentRepo.save(newStudent);
    }

    @Transactional(readOnly=true)
    public List<Student> findStudentsBySchoolName(String name) {
        return studentRepo.findBySchool_Name(name);
    }

    @Transactional(readOnly=true)
    public List<Student> getAllStudents(int page, int count, boolean asc) {
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, count, Sort.by(direction, "id"));
        Page<Student> students = studentRepo.findAll(pageable);
        return students.getContent();
    }

    @Transactional(readOnly=true)
    public Student getStudentById(int id) {
        /* Student student = studentDao.findById(id);

        if (student == null) {
            throw new RecordNotFoundException("Student not found with ID: " + id);
        }
        */

        Student student = studentRepo.findById(id)
            .orElseThrow(() -> new RecordNotFoundException("Student not found with ID: " + id));
        
        return student;
    }

    @Transactional
    public void deleteStudent(int id) {
        Student student = getStudentById(id);
        // studentDao.delete(student);
        studentRepo.delete(student);
    }

    @Transactional(readOnly=true)
    public List<Student> getStudentsByLastName(String lastName) {
        // return studentDao.findByLastName(lastName);
        return studentRepo.findByLastName(lastName);
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

    // SCHOOLS

    @Transactional
    public School insertSchool(School school) {
        return schoolRepo.save(school);
    }

    @Transactional(readOnly=true)
    public School getSchoolById(int id) {
        return schoolRepo.findById(id)
            .orElseThrow(() -> new RecordNotFoundException("School not found with ID: " + id));
    }
}
