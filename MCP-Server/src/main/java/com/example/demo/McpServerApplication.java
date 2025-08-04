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
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService, CNStockService stockService, StockQuoteService stockQuoteService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService, stockService, stockQuoteService).build();
	}
}
