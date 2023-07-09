package com.example.repository;


import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    //2
    @Query("from StudentEntity ")
    List<StudentEntity>getAllStudent();
    //3
    @Query("from StudentEntity where id=?1")
    StudentEntity getStudentById(Integer id);
    //4
    @Transactional
    @Modifying
    @Query("update StudentEntity set name=:name where id=:id")
    int updateStudent(@Param("id") Integer id,
                      @Param("name") String name);
    //5
    @Transactional
    @Modifying
    @Query("delete from StudentEntity where id=?1")
    int deleteStudentById(Integer id);
    //6
    @Query("from StudentEntity where name=?1")
    List<StudentEntity> getStudentByName(String name);
    //7,8
    @Query("from StudentEntity where createdDate>:from and createdDate<:to")
    List<StudentEntity>getStudentListByDate(LocalDateTime from, LocalDateTime to);

    List<StudentEntity>findAllByCreatedDateBetween(LocalDateTime from, LocalDateTime to);




}
