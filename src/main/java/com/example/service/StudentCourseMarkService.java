package com.example.service;

import com.example.dto.StudentCourseMarkDTO;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.exception.ItemNotFoundException;
import com.example.mapper.CourseMapper;
import com.example.mapper.SCMMapper;
import com.example.mapper.StudentMapper;
import com.example.repository.StudentCourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository scmRepository;
    public StudentCourseMarkDTO create(StudentCourseMarkDTO scmDTO) {
        check(scmDTO);
        StudentCourseMarkEntity scmEntity=scmRepository.save(toEntity(scmDTO));
        scmDTO.setId(scmEntity.getId());
        scmDTO.setCreatedDate(scmEntity.getCreatedDate());
       return scmDTO;
    }
    private StudentCourseMarkEntity toEntity(StudentCourseMarkDTO scmDTO){
        StudentCourseMarkEntity scm=new StudentCourseMarkEntity();
        scm.setCourseId(new CourseEntity(scmDTO.getCourseId()));
        scm.setStudentId(new StudentEntity(scmDTO.getStudentId()));
        scm.setMark(scmDTO.getMark());
        return scm;
    }
    private StudentCourseMarkDTO toDto(StudentCourseMarkEntity scmEntity){
        StudentCourseMarkDTO scmDTO=new StudentCourseMarkDTO();
        scmDTO.setId(scmEntity.getId());
        scmDTO.setCreatedDate(scmEntity.getCreatedDate());
        scmDTO.setMark(scmEntity.getMark());
        scmDTO.setStudentId(scmEntity.getStudentId().getId());
        scmDTO.setCourseId(scmEntity.getCourseId().getId());
    return scmDTO;
    }
    private void check(StudentCourseMarkDTO scmDTO){
        if(scmDTO.getStudentId()==null){
            throw new ItemNotFoundException("No student id");
        } else if (scmDTO.getCourseId()==null) {
            throw new ItemNotFoundException(("No course id"));
        } else if (scmDTO.getMark()==null) {
            throw new ItemNotFoundException("No mark was found!");
        }
    }

    public StudentCourseMarkDTO update(StudentCourseMarkDTO scmDTO, Integer id) {
        Optional<StudentCourseMarkEntity> optional=scmRepository.findById(id);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Create entity first");
        }
        StudentCourseMarkEntity scmEntity=optional.get();
        scmEntity.setMark(scmDTO.getMark());
        scmEntity.setCourseId(new CourseEntity(scmDTO.getCourseId()));
        scmEntity.setStudentId(new StudentEntity(scmDTO.getStudentId()));
        System.out.println(scmEntity);
        scmRepository.save(scmEntity);
        scmDTO.setId(scmEntity.getId());
        scmDTO.setCreatedDate(scmEntity.getCreatedDate());
        return scmDTO;
    }

    public StudentCourseMarkDTO getById(Integer id) {
        Optional<StudentCourseMarkEntity> optional=scmRepository.findById(id);
        if(optional.isEmpty()) throw new ItemNotFoundException("Item not found");
        StudentCourseMarkEntity scmEntity=optional.get();
        return toDto(scmEntity);
    }
    public SCMMapper getDetail(Integer id) {
    Optional<StudentCourseMarkEntity>optional=scmRepository.findById(id);
    if(optional.isEmpty()) throw new ItemNotFoundException("Item not found");
    StudentCourseMarkEntity scmEntity=optional.get();
    SCMMapper scmMapper=new SCMMapper();
    scmMapper.setId(scmEntity.getId());
        StudentMapper studentMapper=new StudentMapper();
        studentMapper.setName(scmEntity.getStudentId().getName());
        studentMapper.setSurname(scmEntity.getStudentId().getSurname());
        studentMapper.setId(scmEntity.getStudentId().getId());
    scmMapper.setStudentMapper(studentMapper);
        CourseMapper courseMapper=new CourseMapper();
        courseMapper.setName(scmEntity.getCourseId().getName());
        courseMapper.setId(scmEntity.getCourseId().getId());
        scmMapper.setCourseMapper(courseMapper);
        return scmMapper;
    }

    public String delete(Integer id) {
    getById(id);
        scmRepository.deleteById(id);
    return "Deleted";
    }

    public List<StudentCourseMarkDTO> getAll() {
    Iterable<StudentCourseMarkEntity> iterable=scmRepository.findAll();
    List<StudentCourseMarkDTO> scmList=new LinkedList<>();
    iterable.forEach(s->scmList.add(toDto(s)));
    return scmList;
    }

    public List<StudentCourseMarkDTO> getByDate(LocalDate date, Integer id) {
        List<StudentCourseMarkEntity>entityList=scmRepository.
                findAllByStudentIdAndCreatedDateBetween(new StudentEntity(id),
                LocalDateTime.of(date, LocalTime.MIN),
                LocalDateTime.of(date,LocalTime.MAX));
        return entityList.stream().map(this::toDto).toList();

    }

    public List<StudentCourseMarkDTO> getBetweenDate(LocalDate from, LocalDate to, Integer id) {
        List<StudentCourseMarkEntity>entityList=scmRepository.findAllByStudentIdAndCreatedDateBetween(
                new StudentEntity(id),
                LocalDateTime.of(from,LocalTime.MIN),
                LocalDateTime.of(to,LocalTime.MAX)
        );

        return entityList.stream().map(this::toDto).toList();
    }
}
