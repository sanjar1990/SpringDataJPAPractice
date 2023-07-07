package com.example.repository;


import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity,Integer>, PagingAndSortingRepository<StudentEntity,Integer> {
    List<StudentEntity> getByName(String name);
    List<StudentEntity> getByGender(Gender gender);
    Optional<StudentEntity> getByCreatedDate(LocalDateTime created_date);
    List<StudentEntity> getByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
    Page<StudentEntity> findAllByLevel(Pageable pageable, Integer level);
    Page<StudentEntity>findAllByGender(Pageable pageable, Gender gender);



}
