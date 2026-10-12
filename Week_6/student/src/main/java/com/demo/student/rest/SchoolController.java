package com.demo.student.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.student.domain.School;
import com.demo.student.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/schools")
public class SchoolController {
    private final StudentService studentService;

    public SchoolController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<School> insertSchool(@Validated @RequestBody School school) {
        School savedSchool = studentService.insertSchool(school);
        
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedSchool.getId())
                    .toUri()
            )
            .body(savedSchool);
    }

    @GetMapping("/{id}")
    public School getSchoolById(@PathVariable int id) {
        return studentService.getSchoolById(id);
    }
    
}
