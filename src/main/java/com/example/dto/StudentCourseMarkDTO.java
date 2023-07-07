package com.example.dto;

import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseMarkDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double mark;
    private LocalDateTime createdDate;
}
