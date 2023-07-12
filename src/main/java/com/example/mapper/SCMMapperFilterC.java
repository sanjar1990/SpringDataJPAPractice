package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SCMMapperFilterC {
    private Integer id;
    private String studentName;
    private String courseName;
    private Double mark;
    private LocalDateTime createdDate;
}
