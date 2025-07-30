//WORKING CURL (CMD)
/*
curl -X POST "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions" ^
-H "Authorization: Bearer API KEY HERE" ^
-H "Content-Type: application/json" ^
-d "{\"model\": \"qwen-plus\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"Who are you?\"}]}"
*/
package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

BIG BALLER NO JOHNNY DANG

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${ai.user.input}")
	private String userInput;

	@Bean
	public CommandLineRunner testQuestion(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools,
			ConfigurableApplicationContext context) {

		return args -> {
			try {
				var chatClient = chatClientBuilder
						.defaultToolCallbacks(tools)
						.build();

				System.out.println("\n>>> QUESTION: " + userInput);
				String response = chatClient.prompt(userInput).call().content();
				System.out.println("\n>>> ASSISTANT: " + response);
			} catch (Exception e) {
				System.err.println("Error communicating with AI service: " + e.getMessage());
				e.printStackTrace();
			} finally {
				context.close();
			}
		};
	}
}