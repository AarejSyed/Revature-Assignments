package com.demo.student.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
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
    @Column(name="email", length=45, nullable=false)
    private String email;

    public Student(String firstName, String lastName, @Email @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Student() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
