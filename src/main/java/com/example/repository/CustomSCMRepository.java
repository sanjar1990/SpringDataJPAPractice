package com.example.repository;

import com.example.dto.FilterResultDto;
import com.example.dto.SCMFilterDTO;
import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
import com.example.mapper.SCMMapperFilterC;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Repository
public class CustomSCMRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDto<SCMMapperFilterC>filterPagination(SCMFilterDTO filterDTO, Integer page, Integer size){
        StringBuilder builder=new StringBuilder(" where 1=1");
        StringBuilder selectBuilder=new StringBuilder("select sc.id, s.name as sName, c.name as cName, " +
                " sc.mark, sc.createdDate from StudentCourseMarkEntity as sc inner join sc.courseId as c " +
                "inner join sc.studentId as s");
        StringBuilder countBuilder=new StringBuilder("Select count(sc.id) from StudentCourseMarkEntity as sc inner join sc.courseId as c " +
                "inner join sc.studentId as s");
        Map<String, Object> params=new HashMap<>();


        if(filterDTO.getCourseId()!=null){
            builder.append(" and sc.courseId=:courseId");
            params.put("courseId",new CourseEntity(filterDTO.getCourseId()));
        }
        if(filterDTO.getCourseName()!=null){
            builder.append(" and c.name=:courseName");
            params.put("courseName",filterDTO.getCourseName());
        }
        if(filterDTO.getStudentId()!=null){
            builder.append( " and sc.studentId=:studentId");
            params.put("studentId",new StudentEntity(filterDTO.getStudentId()));
        }

        if(filterDTO.getStudentName()!= null){
            builder.append(" and s.name=:studentName");
            params.put("studentName",filterDTO.getStudentName());
        }
        if(filterDTO.getMarkFrom()!=null){
            builder.append(" and sc.mark>=:markFrom");
            params.put("markFrom",filterDTO.getMarkFrom());
        }
        if(filterDTO.getMarkTo()!=null){
            builder.append(" and sc.mark<=:markTo");
            params.put("markTo",filterDTO.getMarkTo());
        }
        if(filterDTO.getCreatedDateFrom()!=null){
            builder.append(" and sc.createdDate>:fromDate");
            params.put("fromDate", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        if(filterDTO.getCreatedDateTo()!=null){
            builder.append(" and sc.createdDate<:toDate");
            params.put("toDate",LocalDateTime.of(filterDTO.getCreatedDateTo(),LocalTime.MAX));
        }

        selectBuilder.append(builder);
        countBuilder.append(builder);

        Query selectQuery=entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult((page)*size);
        selectQuery.setMaxResults(size);
        Query countQuery=entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String,Object> p: params.entrySet()) {
            selectQuery.setParameter(p.getKey(),p.getValue());
            countQuery.setParameter(p.getKey(),p.getValue());
        }
        Long totalCount=(Long)countQuery.getSingleResult();
        List<Object[]> entityList=selectQuery.getResultList();
        List<SCMMapperFilterC> mapperList=new LinkedList<>();
        for (Object[] o:entityList) {
            SCMMapperFilterC mapper=new SCMMapperFilterC();
            mapper.setId(Integer.valueOf(o[0].toString()));
            mapper.setStudentName(o[1].toString());
            mapper.setCourseName(o[2].toString());
            mapper.setMark(Double.valueOf(o[3].toString()));
            mapper.setCreatedDate(LocalDateTime.parse(o[4].toString()));
            mapperList.add(mapper);
        }
        return new FilterResultDto<>(mapperList,totalCount);
    }
}
