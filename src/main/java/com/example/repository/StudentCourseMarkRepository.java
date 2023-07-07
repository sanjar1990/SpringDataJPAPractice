package com.example.repository;

import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity>findAllByStudentIdAndCreatedDateBetween(StudentEntity id,LocalDateTime from, LocalDateTime to);


}
