package com.example.repository;

import com.example.dto.FilterResultDto;
import com.example.dto.StudentFilterDto;
import com.example.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomStudentRepository {
    @Autowired
    private EntityManager entityManager;

    //12

    public FilterResultDto<StudentEntity>filterPagination(StudentFilterDto filterDto, Integer page, Integer size){
        StringBuilder queryBuilder=new StringBuilder(" where 1=1");
        StringBuilder selectBuilder=new StringBuilder("From StudentEntity as s");
        StringBuilder countBuilder=new StringBuilder("select count(s) from StudentEntity as s");
        Map<String, Object> params=new HashMap<>();
        if(filterDto.getId()!=null){
            queryBuilder.append(" and s.id=:id");
            params.put("id",filterDto.getId());
        }
        if(filterDto.getName()!=null){
            queryBuilder.append(" and s.name=:name");
            params.put("name",filterDto.getName());
        }
        if(filterDto.getSurname() != null){
            queryBuilder.append(" and s.surname=:surname");
            params.put("surname",filterDto.getSurname());
        }
        if(filterDto.getGender() != null){
            queryBuilder.append(" and s.gender=:gender");
            params.put("gender",filterDto.getGender());
        }
        if(filterDto.getAge()!=null){
            queryBuilder.append(" and s.age=:age");
            params.put("age",filterDto.getAge());
        }
        if(filterDto.getLevel()!=null){
            queryBuilder.append(" and s.level=:level");
            params.put("level",filterDto.getLevel());
        }
        if(filterDto.getFromDate()!= null){
            queryBuilder.append(" and s.createdDate>:from");
            params.put("from", LocalDateTime.of(filterDto.getFromDate(), LocalTime.MIN));
        }
        if(filterDto.getToDate() !=null){
            queryBuilder.append(" and s.createdDate<:to");
            params.put("to",LocalDateTime.of(filterDto.getToDate(),LocalTime.MAX));
        }
        selectBuilder.append(queryBuilder);
        countBuilder.append(queryBuilder);
        Query selectQuery=entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult((page)*size);
        selectQuery.setMaxResults(size);
        Query countQuery=entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String,Object> p: params.entrySet()) {
            selectQuery.setParameter(p.getKey(),p.getValue());
            countQuery.setParameter(p.getKey(),p.getValue());
        }
        List<StudentEntity> entityList=selectQuery.getResultList();
        Long totalCount=(Long) countQuery.getSingleResult();
        return new FilterResultDto<>(entityList,totalCount);
    }
}
