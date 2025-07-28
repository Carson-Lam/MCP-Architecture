package com.example.demo;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);

		WeatherService ws = new WeatherService();
		System.out.println(ws.getWeatherForecastByLocation(33.7490, -84.3880));
		
		StockService ss = SpringApplication.run(McpServerApplication.class, args).getBean(StockService.class);
		System.out.println(ss.getStocks());
	
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();	
	}

	@Bean
	public ToolCallbackProvider stockTools(StockService stockService) {
		return MethodToolCallbackProvider.builder().toolObjects(stockService).build();
	}
}
