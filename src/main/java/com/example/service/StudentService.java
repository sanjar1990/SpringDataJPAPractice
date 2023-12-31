package com.example.service;
import com.example.dto.FilterResultDto;
import com.example.dto.StudentDto;
import com.example.dto.StudentFilterDto;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import com.example.exception.AppBadRequestException;
import com.example.exception.ItemNotFoundException;
import com.example.repository.CustomStudentRepository;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CustomStudentRepository customStudentRepository;


    public StudentDto create(StudentDto studentDto) {
        check(studentDto);
        StudentEntity student=toEntity(studentDto);
        studentRepository.save(student);
        studentDto.setId(student.getId());
        studentDto.setCreatedDate(student.getCreatedDate());
        return studentDto;
    }

//    public StudentDto getById(Integer id) {
//        Optional<StudentEntity> optional=studentRepository.findById(id);
//        return optional.map(s->{
//            return toDto(s);
//        }).orElseThrow(()->{throw new ItemNotFoundException("Student not found");
//        });
//    }
    //3
    public StudentDto getById(Integer id) {
        StudentEntity studentEntity=studentRepository.getStudentById(id);
        if(studentEntity==null) throw new ItemNotFoundException("Student not found");
        return toDto(studentEntity);
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

//    public List<StudentDto> getAllStudent() {
//        Iterable<StudentEntity> list=studentRepository.findAll();
//        List<StudentDto> dtoList=new LinkedList<>();
//        list.forEach(s->{
//            dtoList.add(toDto(s));
//        });
//        if(dtoList.isEmpty()) throw new ItemNotFoundException("Student not found");
//
//        return dtoList;
//    }

    //2
    public List<StudentDto> getAllStudent() {
        Iterable<StudentEntity> list=studentRepository.getAllStudent();
        List<StudentDto> dtoList=new LinkedList<>();
        list.forEach(s->{
            dtoList.add(toDto(s));
        });
        if(dtoList.isEmpty()) throw new ItemNotFoundException("Student not found");

        return dtoList;
    }
//    public StudentDto update(StudentDto studentDto, Integer id) {
//        Optional<StudentEntity> optional=studentRepository.findById(id);
//        if(optional.isEmpty()){
//            throw new ItemNotFoundException("Student not found");
//        }
//        StudentEntity studentEntity=optional.get();
//          studentEntity.setName(studentDto.getName());
//        studentEntity.setSurname(studentDto.getSurname());
//        studentEntity.setAge(studentDto.getAge());
//        studentEntity.setGender(studentDto.getGender());
//        studentEntity.setLevel(studentDto.getLevel());
//          studentRepository.save(studentEntity);
//          studentDto.setId(studentEntity.getId());
//          studentDto.setCreatedDate(studentEntity.getCreatedDate());
//          return studentDto;
//    }

    public String update(StudentDto studentDto, Integer id) {
        return studentRepository.updateStudent(id,studentDto.getName())>0?"student updated":"student not updated";
    }

//    public String delete(Integer id) {
//        getById(id);
//        studentRepository.deleteById(id);
//        return "Student deleted";
//    }

    //5
    public String delete(Integer id) {
        getById(id);

        return studentRepository.deleteStudentById(id)>0?"Student deleted":"student not deleted";
    }


//    public List<StudentDto> getByName(String name) {
//        List<StudentEntity> entityList=studentRepository.getByName( name);
//
//        return entityList.stream().map(s->toDto(s)).toList();
//    }
    //6
    public List<StudentDto> getByName(String name) {
        List<StudentEntity> entityList=studentRepository.getStudentByName( name);
        return entityList.stream().map(s->toDto(s)).toList();
    }
    public List<StudentDto> getByGender(Gender data) {
        List<StudentEntity> studentEntity=studentRepository.getByGender(data);
        return studentEntity.stream().map(s->toDto(s)).toList();
    }

//    public List<StudentDto> getByDate(LocalDate createdDate) {
//        List<StudentEntity> entityList= studentRepository.findAllByCreatedDateBetween(LocalDateTime.of(createdDate, LocalTime.MIN),
//                LocalDateTime.of(createdDate,LocalTime.MAX));
//        return entityList.stream().map(s->toDto(s)).collect(Collectors.toList());
//    }
    //7
    public List<StudentDto> getByDate(LocalDate createdDate) {
        List<StudentEntity> entityList= studentRepository.getStudentListByDate(LocalDateTime.of(createdDate, LocalTime.MIN),
                LocalDateTime.of(createdDate,LocalTime.MAX));

        return entityList.stream().map(s->toDto(s)).collect(Collectors.toList());
    }
    public List<StudentDto> getStudentBetweenDate(LocalDate fromDate, LocalDate toDate) {
        List<StudentEntity> studentEntityList=studentRepository.getStudentListByDate(LocalDateTime.of(fromDate.getYear(),fromDate.getMonth(),fromDate.getDayOfMonth(),0,0),
                LocalDateTime.of(toDate.getYear(),toDate.getMonth(),toDate.getDayOfMonth(),0,0));
        if(studentEntityList.isEmpty()) throw new ItemNotFoundException("Students not found");
        List<StudentDto> list=new LinkedList<>();
        studentEntityList.forEach(s->list.add(toDto(s)));
        return list;
    }

    public List<StudentDto> pagination(int page,int size){
        Sort sort=Sort.by(Sort.Direction.ASC,"name");
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<StudentEntity>pageObj=studentRepository.findAll(pageable);
        System.out.println(pageObj.getTotalElements());
        System.out.println(pageObj.getTotalPages());
        return pageObj.getContent().stream().map(s->toDto(s)).toList();
    }
    public List<StudentDto>paginationByLevel(int page, int size, int level){
        Sort sort=Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable=PageRequest.of(page,size,sort);
        Page<StudentEntity> pageObj=studentRepository.findAllByLevel(pageable,level);
        return pageObj.stream().map(s->toDto(s)).toList();
    }
    public List<StudentDto>pagingByGender(int page, int size, Gender gender){
        Sort sort=Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable=PageRequest.of(page,size,sort);
        Page<StudentEntity> pageObj=studentRepository.findAllByGender(pageable,gender);
        return pageObj.stream().map(s->toDto(s)).toList();
    }

    //12
    public FilterResultDto<StudentDto> paginationFilter(StudentFilterDto filterDto, Integer page, Integer size){
        FilterResultDto<StudentEntity> resultDto=customStudentRepository.filterPagination(filterDto,page,size);
        List<StudentDto> dtoList=resultDto.getResultList().stream().map(s->toDto(s)).toList();
        return new FilterResultDto<StudentDto>(dtoList,resultDto.getTotalCount());
    }
}
