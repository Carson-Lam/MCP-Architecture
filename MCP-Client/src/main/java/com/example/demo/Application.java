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

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner chatConversation(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools,
			ConfigurableApplicationContext context) {

		return args -> {
			var chatClient = chatClientBuilder
					.defaultToolCallbacks(tools)
					.build();

			java.util.Scanner scanner = new java.util.Scanner(System.in);
			java.util.ArrayList<String> conversation = new java.util.ArrayList<>();
			conversation.add("system: You are a helpful assistant.");

			System.out.println("\nType your message and press Enter. Type 'exit' to quit.\n");
			while (true) {
				System.out.print(">>> USER: ");
				String userInput = scanner.nextLine();
				if (userInput == null || userInput.trim().equalsIgnoreCase("exit")) {
					break;
				}
				conversation.add("user: " + userInput);
				try {
					StringBuilder promptBuilder = new StringBuilder();
					for (String msg : conversation) {
						promptBuilder.append(msg).append("\n");
					}
					String promptString = promptBuilder.toString();
					String response = chatClient.prompt(promptString).call().content();
					System.out.println("\n>>> ASSISTANT: " + response + "\n");
					conversation.add("assistant: " + response);
				} catch (Exception e) {
					System.err.println("Error communicating with AI service: " + e.getMessage());
					e.printStackTrace();
				}
			}
			scanner.close();
			context.close();
		};
	}
}