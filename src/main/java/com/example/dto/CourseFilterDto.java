package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFilterDto {
    private Integer id;
    private String name;
    private Double price;
    private Integer duration;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
