package com.example.demo;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseApplication {

	//Boilerplate, runs main app
	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

	//Scans for tool annotations in CourseService and registers them
	@Bean
	public List<ToolCallback> myTools(CourseService CourseService) {
		return List.of(ToolCallbacks.from(CourseService)); //ToolCallbacks 
	}

}
