package com.example.repository;

import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.mapper.SCMMapperByMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>,
        PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity>findAllByStudentIdAndCreatedDateBetween(StudentEntity id,LocalDateTime from, LocalDateTime to);
    @Query("select s.mark from StudentCourseMarkEntity as s where s.id=:id order by s.createdDate desc ")
    List<Double> getAllMarks(@Param("id") Integer id);
    @Query("from StudentCourseMarkEntity as scm inner join scm.courseId as c where c.name=:courseName order by scm.createdDate desc")
    List<StudentCourseMarkEntity>getStudentMarkByCourse(@Param("courseName") String courseName);

    @Query("from StudentCourseMarkEntity as sc inner  join  sc.studentId as s where s.id=:id" +
            " order by sc.createdDate desc limit 1")
    StudentCourseMarkEntity getStudentLastMark(@Param("id") Integer id);

    @Query("select new com.example.mapper.SCMMapperByMark(sc.id, sc.mark, c.name) from StudentCourseMarkEntity as sc" +
            " inner join sc.courseId as c inner  join  sc.studentId as s " +
            "where s.id=:id" +
            " order by sc.mark desc limit 3")
    List<SCMMapperByMark> top3Mark(@Param("id") Integer studentId);

    @Query("select new com.example.mapper.SCMMapperByMark(sc.id, sc.mark, c.name) from StudentCourseMarkEntity  as sc" +
            " inner join sc.courseId as c inner  join sc.studentId as s where s.id=?1 " +
            " order by sc.createdDate limit 1")
    SCMMapperByMark firstMark(Integer studentId);

    @Query("select new com.example.mapper.SCMMapperByMark(sc.id, sc.mark, c.name) " +
            "from StudentCourseMarkEntity as sc inner join sc.courseId as c " +
            "inner join sc.studentId as s where s.id=:id and c.name=:name " +
            "order by sc.createdDate limit 1")
    SCMMapperByMark firstMarkByCourseName(@Param("id") Integer studentId,
            @Param("name") String courseName);

    @Query("select new com.example.mapper.SCMMapperByMark(sc.id, sc.mark, c.name)" +
            " from StudentCourseMarkEntity  as sc inner join sc.studentId as s inner join " +
            "sc.courseId as c where s.id=:id and c.name=:name" +
            " order by sc.mark desc  limit 1")
    SCMMapperByMark topMarkByCourse(@Param("id") Integer studentId,
                                    @Param("name") String courseName );
    //16

    @Query("select avg (sc.mark) from StudentCourseMarkEntity as sc inner join sc.studentId as s where s.id=?1")
    Double averageMark(Integer studentId);
    //17
    @Query("select avg (sc.mark) from StudentCourseMarkEntity  as sc " +
            "inner join sc.courseId as c inner join sc.studentId as s " +
            "where s.id=:id and c.name=:name")
    Double averageMarkByCourse(@Param("id") Integer studentId,
                               @Param("name")String courseName);
    //18
    @Query("select count (sc.mark) from StudentCourseMarkEntity as sc " +
            "inner join sc.studentId as s where s.id=:id and sc.mark>:mark")
    Integer  greaterMark(@Param("id") Integer studentId,
                         @Param("mark") Double mark);

    //19
    @Query("select max (sc.mark) from StudentCourseMarkEntity as sc " +
            "inner join sc.courseId as c where c.name=:name")
    Double getTopMarkByCourse(@Param("name") String courseName);
    //20
    @Query("select avg (sc.mark) from StudentCourseMarkEntity as sc " +
            "inner join sc.courseId as c where c.name=:name")
    Double getAverageMarkByCourse(@Param("name") String courseName);
    //21
    @Query("select count (sc.mark) from StudentCourseMarkEntity as sc " +
            "inner join sc.courseId as c where c.name=:name")
    Long getTotalMarkByCourse(@Param("name")String courseName);
    //23
    Page<StudentCourseMarkEntity>findAllByStudentId(StudentEntity studentEntity, Pageable pageable);
    Page<StudentCourseMarkEntity>findAllByCourseId(CourseEntity courseEntity,Pageable pageable);



}
