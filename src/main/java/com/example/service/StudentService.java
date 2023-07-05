package com.example.service;

import com.example.dto.StudentDto;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import com.example.exception.AppBadRequestException;
import com.example.exception.ItemNotFoundException;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;


    public StudentDto create(StudentDto studentDto) {
        check(studentDto);
        StudentEntity student=toEntity(studentDto);
        studentRepository.save(student);
        studentDto.setId(student.getId());
        studentDto.setCreatedDate(student.getCreatedDate());
        return studentDto;
    }

    public StudentDto getById(Integer id) {
        Optional<StudentEntity> optional=studentRepository.findById(id);
        return optional.map(s->{
            return toDto(s);
        }).orElseThrow(()->{throw new ItemNotFoundException("Student not found");
        });
    }

    public StudentDto toDto(StudentEntity studentEntity){
        StudentDto studentDto=new StudentDto();
        studentDto.setName(studentEntity.getName());
        studentDto.setSurname(studentEntity.getSurname());
        studentDto.setGender(studentEntity.getGender());
        studentDto.setAge(studentEntity.getAge());
        studentDto.setLevel(studentEntity.getLevel());
        studentDto.setCreatedDate(studentEntity.getCreatedDate());
        studentDto.setId(studentEntity.getId());
        return studentDto;
    }

    public void check(StudentDto studentDto){
        if(studentDto.getName()==null || studentDto.getName().isBlank()){
            throw new AppBadRequestException("No name was found");
        } else if (studentDto.getSurname()==null || studentDto.getSurname().isBlank()) {
            throw new AppBadRequestException("No surname was found");
        }else if(studentDto.getAge()==null){
            throw new AppBadRequestException("No age was found");
        } else if (studentDto.getGender()==null) {
            throw new AppBadRequestException("No gender was found");
        } else if (studentDto.getLevel()==null) {
            throw new AppBadRequestException("No level was found");
        }
    }
    public StudentEntity toEntity(StudentDto dto){
        StudentEntity entity= new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setGender(dto.getGender());
        entity.setLevel(dto.getLevel());
        return entity;
    }

    public List<StudentDto> createAll(List<StudentDto> studentDtoList) {
        studentDtoList.forEach(s->{
            check(s);
            StudentEntity entity=toEntity(s);
            studentRepository.save(entity);
            s.setId(entity.getId());
            s.setCreatedDate(entity.getCreatedDate());
        });
        return studentDtoList;
    }

    public List<StudentDto> getAllStudent() {
        Iterable<StudentEntity> list=studentRepository.findAll();
        List<StudentDto> dtoList=new LinkedList<>();
        list.forEach(s->{
            dtoList.add(toDto(s));
        });
        if(dtoList.isEmpty()) throw new ItemNotFoundException("Student not found");

        return dtoList;
    }

    public Object update(StudentDto studentDto, Integer id) {
        Optional<StudentEntity> optional=studentRepository.findById(id);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        StudentEntity studentEntity=optional.get();
          studentEntity.setName(studentDto.getName());
          studentEntity.setSurname(studentDto.getSurname());
          studentEntity.setAge(studentDto.getAge());
          studentEntity.setGender(studentDto.getGender());
          studentEntity.setLevel(studentDto.getLevel());
          studentRepository.save(studentEntity);
          studentDto.setId(studentEntity.getId());
          studentDto.setCreatedDate(studentEntity.getCreatedDate());
          return studentDto;
    }

    public String delete(Integer id) {
        getById(id);
        studentRepository.deleteById(id);
        return "Student deleted";
    }

    public Object getByName(String name) {
        Optional<StudentEntity> optional=studentRepository.getByName( name);
        if(optional.isEmpty()) throw new ItemNotFoundException("Student not found");
        return optional.map(s->toDto(s)).get();
    }


    public Object getByGender(Gender data) {
        StudentEntity studentEntity=studentRepository.getByGender(data);
        return toDto(studentEntity);
    }

    public StudentDto getByDate(LocalDateTime createdDate) {
       Optional<StudentEntity> optional= studentRepository.getByCreatedDate(createdDate);
       if(optional.isEmpty()){
           throw new ItemNotFoundException("Student not found");
       }
       return toDto(optional.get());
    }
    public List<StudentEntity> getStudentBetweenDate(LocalDate fromDate, LocalDate toDate) {
        List<StudentEntity> studentEntityList=studentRepository.getByCreatedDateBetween(LocalDateTime.of(fromDate.getYear(),fromDate.getMonth(),fromDate.getDayOfMonth(),0,0),
                LocalDateTime.of(toDate.getYear(),toDate.getMonth(),toDate.getDayOfMonth(),0,0));
        if(studentEntityList.isEmpty()) throw new ItemNotFoundException("Students not found");

        return studentEntityList;
    }
}
