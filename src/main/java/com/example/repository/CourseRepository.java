package com.example.repository;

import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer>, PagingAndSortingRepository<CourseEntity,Integer> {
    Optional<CourseEntity> getByName(String name);

    List<CourseEntity> getByPrice(Double price);
    List<CourseEntity> getByDuration(Integer duration);
    List<CourseEntity> getByPriceBetween(Double fromPrice, Double toPrice);
    List<CourseEntity>getByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
    Page<CourseEntity>findAllByPrice(Pageable pageable, Double price);
    Page<CourseEntity>findAllByPriceBetween(Pageable pageable, Double from, Double to);

    //select s.name, s.surname from student as s
}
