package com.example.controller;

import com.example.dto.StudentCourseMarkDTO;
import com.example.repository.StudentRepository;
import com.example.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/scm")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService scmService;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/create")

    public ResponseEntity<?> create(@RequestBody StudentCourseMarkDTO scmDTO){
        return ResponseEntity.ok(scmService.create(scmDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>update(@RequestBody StudentCourseMarkDTO scmDTO,
                                   @PathVariable Integer id){
        return ResponseEntity.ok(scmService.update(scmDTO,id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.getById(id));
    }
    @GetMapping("/withDetail/{id}")
    public ResponseEntity<?>getByIdWithDetail(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.getDetail(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.delete(id));
    }
    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(scmService.getAll());
    }
    @GetMapping("/getByDate/{id}")
    public ResponseEntity<?>getByDate(@RequestParam("date")LocalDate date,
                                      @PathVariable Integer id){
        return ResponseEntity.ok(scmService.getByDate(date, id));
    }
    @GetMapping("/getBetwenDate/{id}")
    public ResponseEntity<?>getBetweenDate(@RequestParam("from") LocalDate from,
                                           @RequestParam("to") LocalDate to,
                                           @PathVariable Integer id){
        return ResponseEntity.ok(scmService.getBetweenDate(from, to, id));
    }
}
