package com.example.entity;

import com.example.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 25)
    private String name;
    @Column(name = "surname", length = 25)
    private String surname;
    @Column(name = "level")
    private Integer level;
    @Column(name = "age")
    private Integer age;
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public StudentEntity(Integer id) {
        this.id=id;
    }

    public StudentEntity() {

    }
}
