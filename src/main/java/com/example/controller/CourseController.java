package com.example.controller;

import com.example.dto.CourseDto;
import com.example.dto.CourseFilterDto;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody CourseDto courseDto){
        return ResponseEntity.ok(courseService.create(courseDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable Integer id){
        return ResponseEntity.ok(courseService.getById(id));
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCourse(){
        return ResponseEntity.ok(courseService.getAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer id,
                                          @RequestBody CourseDto courseDto){
        return ResponseEntity.ok(courseService.update(courseDto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id){
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam("name") String name ){
        return ResponseEntity.ok(courseService.getByName(name));
    }
    @GetMapping("/price")
    public ResponseEntity<?> getByPrice(@RequestParam("price") Double price){
        return ResponseEntity.ok(courseService.getByPrice(price));
    }
    @GetMapping("/duration")
    public ResponseEntity<?>getByDuration(@RequestParam("duration") Integer duration){
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }
    @GetMapping("/betweenPrice")
    public ResponseEntity<?> getBetweenPrice(@RequestParam("fromPrice") Double fromPrice,
                                             @RequestParam("toPrice") Double toPrice){
        return ResponseEntity.ok(courseService.getCourseBetweenPrice(fromPrice, toPrice));
    }
    @GetMapping("/getBetweenDate")
    public ResponseEntity<?> getBetweenDate(@RequestParam("fromDate")LocalDate fromDate,
                                            @RequestParam("toDate") LocalDate toDate){
        return ResponseEntity.ok(courseService.getBetweenDate(fromDate,toDate));
    }

    @GetMapping("/pagination")
    public ResponseEntity<?>pagination(@RequestParam("page") int page,
                                       @RequestParam("size") int size){
        return ResponseEntity.ok(courseService.pagination(page-1,size));
    }
    @GetMapping("/paginSort")
    public ResponseEntity<?> pagingSort(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam("sort") String sort){
        return ResponseEntity.ok(courseService.paginationSorted(page-1,size,sort));
    }
    @GetMapping("/pagePrice")
    public ResponseEntity<?> pageByPrice(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("price") double price){
        return ResponseEntity.ok(courseService.pageByPrice(page-1,size,price));
    }
    @GetMapping("/pagePriceBetween")
    public ResponseEntity<?> pagePriceBetween(@RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("from") double from,
                                              @RequestParam("to") double to){
        return ResponseEntity.ok(courseService.pageByPriceBetween(page-1,size,from,to));
    }

    @PostMapping("/filterPagination")
    public ResponseEntity<?>filterPagination(@RequestBody CourseFilterDto courseFilterDto,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("size") Integer size){
        return ResponseEntity.ok(courseService.filterPagination(courseFilterDto,page-1,size));
    }
}
