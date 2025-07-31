//java -jar "C:\Users\lliu8\Documents\Carson - Coding projects\MCP\MCP-Server\target\MCPServer-0.0.1-SNAPSHOT.jar"
package com.example.demo;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);

		// Test services
		// WeatherService ws = new WeatherService();
		// System.out.println(ws.getWeatherForecastByLocation(33.7490, -84.3880));
		
		// StockService ss = new StockService();
		// System.out.println(ss.getStocks());
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService, StockService stockService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService, stockService).build();
	}
}
