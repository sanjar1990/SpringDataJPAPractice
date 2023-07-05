package com.example.repository;

import com.example.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer> {
    Optional<CourseEntity> getByName(String name);

    List<CourseEntity> getByPrice(Double price);

    List<CourseEntity> getByDuration(Integer duration);
    List<CourseEntity> getByPriceBetween(Double fromPrice, Double toPrice);
    List<CourseEntity>getByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
}