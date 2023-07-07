package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SCMMapper {
    private Integer id;
    private StudentMapper studentMapper;
    private CourseMapper courseMapper;
}
