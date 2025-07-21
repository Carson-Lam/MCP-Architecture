package com.example.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseService{
    //LogFactory interface, list interface
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);
    private List<Course> courses = new ArrayList<>();   

    //Tool for getting list of courses
    @Tool(name = "CL_get_tutorials", description="gets a list of courses")
    public List<Course> getCourses() {
        return courses;
    } 

    //Tool for getting a single course
    @Tool(name = "CL_get_tutorial", description = "get a single course by course title")
    public Course getCourse(String title){
        return courses.stream()
            .filter(course -> course.title().equals(title))
            .findFirst()
            .orElse(null);
    }


    @PostConstruct //Tags constructor to run after all beans initialized
    public void init() {
        courses.addAll(List.of(
            new Course("Tool annotation", "https://docs.spring.io/spring-ai/reference/api/tools.html"),
            new Course("PostConstruct annotation", "https://medium.com/must-read-articles-in-a-minute/preconstruct-and-postconstruct-annotation-12ff7c868f8b"),
            new Course("SpringBootApplication annotation", "https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/html/using-boot-using-springbootapplication-annotation.html")
        ));
    }
}