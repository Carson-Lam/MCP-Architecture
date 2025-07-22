package com.example.demo;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockApplication {

	//Boilerplate, runs main app
	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

	@Bean
	public List<ToolCallback> myTools(StockService stockService) {
		System.err.println("DEBUG: Registered tools");
		List<ToolCallback> callbacks = List.of(ToolCallbacks.from(stockService));
		System.err.println("DEBUG: Tool count: " + callbacks.size()); //Show amount of tools retrieved
		return callbacks;
	}

	//Debug command line runner
	// @Bean
	// public CommandLineRunner testTools(CourseService courseService) {
	// 	return args -> {
	// 		System.out.println("Testing tools...");
	// 		System.out.println("Courses: " + courseService.getCourses());
	// 		System.out.println("Single course: " + courseService.getCourse("Tool annotation"));
	// 	};
	// }

	// MANUALLY TEST JAR: java -jar target/demo-0.0.1-SNAPSHOT.jar
	
}