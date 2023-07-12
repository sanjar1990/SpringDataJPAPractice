package com.example.controller;

import com.example.dto.SCMFilterDTO;
import com.example.dto.StudentCourseMarkDTO;
import com.example.repository.StudentRepository;
import com.example.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    @GetMapping("/getMarks/{id}")
    public ResponseEntity<?>getMarks(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.studentMark(id));
    }
    @GetMapping("/getByCourse")
    public ResponseEntity<?> getByCourse(@RequestParam("courseName") String  courseName){
        return ResponseEntity.ok(scmService.getByCourse(courseName));
    }

    @GetMapping("/getLastMark/{id}")
    public ResponseEntity<?>getLastMark(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.getLastMark(id));
    }

    @GetMapping("/top3Mark/{id}")
    public ResponseEntity<?>top3Mar(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.top3Mark(id));
    }

    @GetMapping("/firstMark/{id}")
    public ResponseEntity<?>firstMark(@PathVariable Integer id){
        return ResponseEntity.ok(scmService.firstMark(id));
    }

    @GetMapping("/firstMarkByCourseName")
    public ResponseEntity<?>firstMarkByCourseName(@RequestParam("courseName") String courseName,
                                                  @RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(scmService.firstMarkByCourseName(courseName,studentId));
    }

    @GetMapping("/topMarkByCourse")
    public ResponseEntity<?>topMarkByCourse(@RequestParam("studentId") Integer studentId,
                                            @RequestParam("corseName") String courseName){
        return ResponseEntity.ok(scmService.topMarkByCourse(studentId,courseName));
    }

    @GetMapping("/averageMark/{studentId}")
    public ResponseEntity<?>averageMark(@PathVariable Integer studentId){
        return ResponseEntity.ok(scmService.averageMark(studentId));
    }
    @GetMapping("/avgMarkByCourse")
    public ResponseEntity<?>averageMarkByCourse(@RequestParam("studentId") Integer studentId,
                                                @RequestParam("courseName") String courseName){
        return ResponseEntity.ok(scmService.averageMarkByCourse(studentId,courseName));
    }

    @GetMapping("/greaterMark")
    public ResponseEntity<?>greaterMark(@RequestParam("studentId") Integer studentId,
                                        @RequestParam("mark") Double mark){
        return ResponseEntity.ok(scmService.greaterMark(studentId,mark));
    }

    @GetMapping("/getTopMarkByCourse")
    public ResponseEntity<?>getTopMarkByCourse(@RequestParam("courseName") String courseName){
        return ResponseEntity.ok(scmService.getTopMarkByCourse(courseName));
    }
    @GetMapping("/getAverageMarkByCourse")
    public ResponseEntity<?> getAverageMarkByCourse(@RequestParam("name") String courseName){
        return ResponseEntity.ok(scmService.getAverageMarkByCourse(courseName));
    }
    @GetMapping("/getTotalMarkByCourse")
    public ResponseEntity<?> getTotalMarkByCourse(@RequestParam("courseName") String courseName){
        return ResponseEntity.ok(scmService.getTotalMarkByCourse(courseName));
    }

    @GetMapping("/pagination")
    public ResponseEntity<?>pagination(@RequestParam("page") Integer page,
                                       @RequestParam("size") Integer size){
        return ResponseEntity.ok(scmService.pagination(page-1,size));
    }

    @GetMapping("/paginationByStudentId")
    public ResponseEntity<?>paginationByStudentId(@RequestParam("studentId") Integer studentId,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size){
        return ResponseEntity.ok(scmService.paginationByStudentId(studentId,page,size));
    }

    @GetMapping("/paginationByCourseId")
    public ResponseEntity<?>paginationByCourseId(@RequestParam("courseId") Integer courseId,
                                                 @RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size){
        return ResponseEntity.ok(scmService.paginationByCourseId(courseId,page-1,size));
    }

    @PostMapping("/filterPagination")

    public ResponseEntity<?>filterPagination(@RequestBody SCMFilterDTO filterDTO,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("size") Integer size){
        return ResponseEntity.ok(scmService.filterPagination(filterDTO,page-1,size));
    }




}
