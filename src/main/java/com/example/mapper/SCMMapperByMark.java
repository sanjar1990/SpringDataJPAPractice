package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SCMMapperByMark {
    private Integer id;
    private Double mark;
    private String courseName;

    public SCMMapperByMark(Integer id, Double mark, String courseName) {
        this.id = id;
        this.mark = mark;
        this.courseName = courseName;
    }
}
