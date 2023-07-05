package com.example.repository;


import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity,Integer> {


    Optional<StudentEntity> getByName(String name);
    StudentEntity getByGender(Gender gender);
    Optional<StudentEntity> getByCreatedDate(LocalDateTime created_date);
    List<StudentEntity> getByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
