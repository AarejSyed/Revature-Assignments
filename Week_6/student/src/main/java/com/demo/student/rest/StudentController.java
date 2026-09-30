package com.demo.student.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.student.domain.Student;
import com.demo.student.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
        ANTI-PATTERNS - DON'T DO STUFF LIKE THIS
        http://localhost:8080/students/...
            getstudent
            deletestudent
            updatestudent
    */

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    
    @PostMapping
    public ResponseEntity<Student> insertStudent(@Validated @RequestBody Student student) {
        Student savedStudent = studentService.insertStudent(student);
        
        // localhost:8080/api/students/{id}
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedStudent.getId())
                    .toUri()
            )
            .body(savedStudent);
    }

    // Route parameter - typically used for resource location
    // GET http://localhost:8080/api/students/3
    // Query parameter - typically used for filtering/searching
    // GET http://localhost:8080/api/students?lastName=wilson&grade=b&...
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // GET http://localhost:8080/api/students?lastName=wilson
    @GetMapping(params="lastName")
    public List<Student> getStudentsByLastName(@RequestParam String lastName) {
        return studentService.getStudentsByLastName(lastName);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
}
