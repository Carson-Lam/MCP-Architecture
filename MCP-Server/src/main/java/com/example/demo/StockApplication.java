package com.example.demo;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockApplication {

	//Boilerplate, runs main app
	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
		StockService ss = new StockService();
		System.out.println(ss.getStocks());
	}

	@Bean
	public ToolCallbackProvider stockTools(StockService stockService) {
		return MethodToolCallbackProvider.builder().toolObjects(stockService).build();
	}

}