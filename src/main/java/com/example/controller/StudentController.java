package com.example.controller;

import com.example.dto.StudentDto;
import com.example.dto.StudentFilterDto;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody StudentDto studentDto){
        return ResponseEntity.ok(studentService.create(studentDto));
    }
    @PostMapping("/createAll")
    public ResponseEntity<?> createAll(@RequestBody List<StudentDto> studentDtoList){
        return ResponseEntity.badRequest().body(studentService.createAll(studentDtoList));
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(studentService.getAllStudent());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        return new ResponseEntity<StudentDto>(studentService.getById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>updateStudent(@RequestBody StudentDto studentDto,
                                          @PathVariable Integer id ){
        return ResponseEntity.ok(studentService.update(studentDto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return new ResponseEntity<String>(studentService.delete(id),HttpStatus.OK);
    }
    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam("name") String name){
        return ResponseEntity.ok(studentService.getByName(name));
    }
    @GetMapping("/gender")
    public ResponseEntity<?> getByGender(@RequestParam("gender") Gender gender){
        return ResponseEntity.ok(studentService.getByGender(gender));
    }
    @GetMapping("/date")
    public ResponseEntity<?> getByDate(@RequestParam("date")LocalDate createdDate){
        return ResponseEntity.ok(studentService.getByDate(createdDate));
    }
    @GetMapping("/between")
    public ResponseEntity<?> getBetweenDate(@RequestParam("fromDate") LocalDate fromDate,
                                            @RequestParam("toDate") LocalDate toDate){
        return ResponseEntity.ok(studentService.getStudentBetweenDate(fromDate,toDate));
    }

    @GetMapping("/pagination")
    public ResponseEntity<?>pagination(@RequestParam("page") int page,
                                       @RequestParam("size") int size){
        return ResponseEntity.ok(studentService.pagination(page-1,size));
    }

    @GetMapping("/pageByLevel")
    public ResponseEntity<?>pageByLevel(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam("level") int level){
        return ResponseEntity.ok(studentService.paginationByLevel(page-1,size,level));
    }

    @GetMapping("/pageByGender")
    public ResponseEntity<?>pageByGender(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("gender") Gender gender){
        return ResponseEntity.ok(studentService.pagingByGender(page-1,size,gender));
    }

    @PostMapping("/filterPagination")
    public ResponseEntity<?> filterPagination(@RequestBody StudentFilterDto studentFilterDto,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size){
        return ResponseEntity.ok(studentService.paginationFilter(studentFilterDto,page-1,size));
    }

}
