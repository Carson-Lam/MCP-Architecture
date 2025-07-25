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
		StockService ss = new StockService();
		
	}

	@Bean
	public ToolCallbackProvider stockTools(StockService stockService) {
		return  MethodToolCallbackProvider.builder().toolObjects(stockService).build();
	}

}