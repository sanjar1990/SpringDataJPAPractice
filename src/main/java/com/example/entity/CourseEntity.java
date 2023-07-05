package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "course_name",length = 100)
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
