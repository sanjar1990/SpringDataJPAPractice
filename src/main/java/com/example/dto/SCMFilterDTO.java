package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SCMFilterDTO {
    private Integer studentId;
    private Integer courseId;
    private Double markFrom;
    private Double markTo;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private String studentName;
    private String courseName;
}
