package com.example.service;

import com.example.dto.CourseDto;
import com.example.dto.StudentDto;
import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
import com.example.exception.AppBadRequestException;
import com.example.exception.ItemNotFoundException;
import com.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public CourseDto create(CourseDto courseDto) {
        check(courseDto);
        CourseEntity courseEntity=toEntity(courseDto);
        courseRepository.save(courseEntity);
        courseDto.setCreatedDate(courseEntity.getCreatedDate());
        courseDto.setId(courseEntity.getId());
        return courseDto;
    }
    public CourseDto getById(Integer id) {
        Optional<CourseEntity> optional=courseRepository.findById(id);
        return optional.map(s->toDto(s)).orElseThrow(()->new ItemNotFoundException("Student not found"));
    }

    public CourseDto toDto(CourseEntity courseEntity){
      CourseDto courseDto= new CourseDto();
      courseDto.setName(courseEntity.getName());
      courseDto.setPrice(courseEntity.getPrice());
      courseDto.setDuration(courseEntity.getDuration());
      courseDto.setId(courseEntity.getId());
      courseDto.setCreatedDate(courseEntity.getCreatedDate());
      return courseDto;
    }

    public CourseEntity toEntity(CourseDto courseDto){
        CourseEntity courseEntity= new CourseEntity();
        courseEntity.setName(courseDto.getName());
        courseEntity.setDuration(courseDto.getDuration());
        courseEntity.setPrice(courseDto.getPrice());
        courseEntity.setCreatedDate(LocalDateTime.now());
        return courseEntity;
    }

    public void check(CourseDto courseDto){
        if(courseDto.getName()==null || courseDto.getName().isBlank()){
            throw new AppBadRequestException("No course name found!");
        } else if (courseDto.getPrice()==null) {
            throw new AppBadRequestException("No course price found!");
        } else if (courseDto.getDuration()==null) {
            throw new AppBadRequestException("No course duration found!");
        }
    }


    public List<CourseDto> getAll() {
        List<CourseDto> courseDtoList=new LinkedList<>();
        Iterable<CourseEntity> iterable=courseRepository.findAll();
        iterable.forEach(s->{
            courseDtoList.add(toDto(s));
        });
        return courseDtoList;
    }

    public CourseDto update(CourseDto courseDto, Integer id) {
        Optional<CourseEntity> optional=courseRepository.findById(id);
        if(optional.isEmpty()) throw new ItemNotFoundException("Course not found");
        CourseEntity courseEntity=optional.get();
        courseEntity.setName(courseDto.getName());
        courseEntity.setPrice(courseDto.getPrice());
        courseEntity.setDuration(courseDto.getDuration());
        courseRepository.save(courseEntity);
        courseDto.setCreatedDate(courseEntity.getCreatedDate());
        courseDto.setId(courseEntity.getId());
        return courseDto;
    }

    public String delete(Integer id) {
        courseRepository.deleteById(id);
        return "course is deleted!";
    }

    public CourseDto getByName(String name) {
        Optional<CourseEntity> optional=courseRepository.getByName(name);
        return optional.map(s->toDto(s)).orElseThrow(()-> new ItemNotFoundException("course not found"));
    }

    public Object getByPrice(Double price) {
        List<CourseEntity> courseEntityList=courseRepository.getByPrice(price);
        List<CourseDto>list=new LinkedList<>();
        courseEntityList.forEach(s->list.add(toDto(s)));
        if(list.isEmpty()) throw new ItemNotFoundException("Course not found");
        return list;
    }

    public List<CourseDto> getByDuration(Integer duration) {
        List<CourseEntity>courseEntities=courseRepository.getByDuration(duration);
        List<CourseDto>list=new LinkedList<>();
        courseEntities.forEach(s->list.add(toDto(s)));
        if(list.isEmpty()) throw new ItemNotFoundException("course not found");
        return list;
    }

    public List<CourseDto> getCourseBetweenPrice(Double fromPrice, Double toPrice) {
        List<CourseEntity> courseEntityList=courseRepository.getByPriceBetween(fromPrice,toPrice);
        List<CourseDto>courseDtoList=new LinkedList<>();
        courseEntityList.forEach(s->courseDtoList.add(toDto(s)));
        if(courseDtoList.isEmpty())throw new ItemNotFoundException("Course not found");
        return courseDtoList;
    }

    public List<CourseDto> getBetweenDate(LocalDate fromDate, LocalDate toDate) {
        List<CourseEntity> courseEntityList=courseRepository.getByCreatedDateBetween(LocalDateTime.of(fromDate.getYear(),fromDate.getMonth(),
                fromDate.getDayOfMonth(),0,0),LocalDateTime.of(toDate.getYear(),toDate.getMonth(),toDate.getDayOfMonth(),0,0));
        List<CourseDto> courseDtoList=new LinkedList<>();
        courseEntityList.forEach(s->courseDtoList.add(toDto(s)));
        if(courseDtoList.isEmpty()) throw new ItemNotFoundException("Course not found");
        return courseDtoList;
    }
}
