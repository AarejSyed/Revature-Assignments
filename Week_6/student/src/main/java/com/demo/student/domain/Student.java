package com.demo.student.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name="student")
public class Student {
    /*
        Strategy Types
        IDENTITY - uses DB auto-increment/identity column
        SEQUENCE - use DB-provided sequence
        TABLE - JPA uses table to simulate sequence
        AUTO - JPA/provider chooses the strategy automatically
    */
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="school_id")
    private School school;

    @OneToMany(
        mappedBy="student",
        fetch=FetchType.LAZY,
        orphanRemoval=true,
        cascade=CascadeType.ALL
    )
    private List<Enrollment> enrollments = new ArrayList<>();

    @Size(max=20)
    @Column(name="first_name", length=45)
    private String firstName;

    @Size(max=20)
    @Column(name="last_name", length=45)
    private String lastName;

    // Worth being aware of:
    //  @NotNull
    //  @NotBlank
    //  @NotEmpty
    //  @Size
    //  @Min
    //  @Max
    //  @Positive
    //  @PositiveOrZero
    //  @Email
    //  @Pattern
    
    // By default, nullable is true
    @Email
    @NotBlank   // Ensure not null, empty, or whitespace
    @Column(name="email", length=45, nullable=false, unique=true)
    private String email;

    public Student(String firstName, String lastName, @Email @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Student(String firstName, String lastName, @Email @NotBlank String email, School school) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
    }

    public Student() {}

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    public void removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }
}
