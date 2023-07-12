package com.example.repository;

import com.example.dto.CourseFilterDto;
import com.example.dto.FilterResultDto;
import com.example.entity.CourseEntity;
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
public class CustomCourseRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDto<CourseEntity>filterPagination(CourseFilterDto filterDto, Integer page, Integer size){
        StringBuilder builder=new StringBuilder(" where 1=1");
        StringBuilder selectBuilder=new StringBuilder("From CourseEntity as c");
        StringBuilder countBuilder=new StringBuilder("select count(c) from CourseEntity as c");
        Map<String,Object> params=new HashMap<>();
        if(filterDto.getName()!=null){
            builder.append(" and c.name=:name");
            params.put("name",filterDto.getName());
        }
        if(filterDto.getPrice()!=null){
            builder.append(" and c.price=:price");
            params.put("price",filterDto.getPrice());
        }
        if(filterDto.getDuration()!=null){
            builder.append(" and c.duration=:duration");
            params.put("duration",filterDto.getDuration());
        }
        if(filterDto.getId()!=null){
            builder.append(" and c.id=:id");
            params.put("id",filterDto.getId());
        }
        if(filterDto.getCreatedDateFrom()!= null){
            builder.append(" and c.createdDate>:from");
            params.put("from", LocalDateTime.of(filterDto.getCreatedDateFrom(), LocalTime.MIN));
        }
        if(filterDto.getCreatedDateTo()!=null){
            builder.append(" and c.createdDate<:to");
            params.put("to",LocalDateTime.of(filterDto.getCreatedDateTo(),LocalTime.MAX));
        }
        selectBuilder.append(builder);
        countBuilder.append(builder);
        Query selectQuery=entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult((page)*size);
        selectQuery.setMaxResults(size);
        Query countQuery=entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String,Object> p:params.entrySet()) {
            selectQuery.setParameter(p.getKey(),p.getValue());
            countQuery.setParameter(p.getKey(),p.getValue());
        }
        List<CourseEntity> entityList=selectQuery.getResultList();
        Long totalCount=(Long)countQuery.getSingleResult();
        return new FilterResultDto<CourseEntity>(entityList,totalCount);
    }
}
