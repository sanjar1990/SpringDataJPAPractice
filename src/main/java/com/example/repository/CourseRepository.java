package com.example.repository;

import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer>, PagingAndSortingRepository<CourseEntity,Integer> {
    Optional<CourseEntity> getByName(String name);

//    List<CourseEntity> getByPrice(Double price);
    List<CourseEntity> getByDuration(Integer duration);
    List<CourseEntity> getByPriceBetween(Double fromPrice, Double toPrice);
    List<CourseEntity>getByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
    Page<CourseEntity>findAllByPrice(Pageable pageable, Double price);
    Page<CourseEntity>findAllByPriceBetween(Pageable pageable, Double from, Double to);

    //select s.name, s.surname from student as s
    //2
    @Query("from CourseEntity where id=?1")
    CourseEntity getCourseById(Integer id);
    //3

    @Query("from CourseEntity ")
    List<CourseEntity>getAllCourse();

    //4
    @Transactional
    @Modifying
    @Query("update CourseEntity set name=:name where id=:id")
    int updateCourse(@Param("id") Integer id,
                     @Param("name") String name);
    //5

    @Transactional
    @Modifying
    @Query("delete from CourseEntity where id=?1")
    int deleteCourse(Integer id);

    //6
    @Query("from CourseEntity where price=?1")
    List<CourseEntity>getByPrice(Double price);

    //7
    @Query("from CourseEntity where price>:from and price<:to")
    List<CourseEntity>getCourseBetweenPrice(@Param("from") Double from,
                                            @Param("to") Double to);
    //8
    @Query("from CourseEntity where createdDate>:from and createdDate<:to")
    List<CourseEntity>getCourseByDateBetween(@Param("from") LocalDateTime from,
                                             @Param("to") LocalDateTime to);

}
