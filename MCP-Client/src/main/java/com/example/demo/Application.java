//WORKING CURL (CMD)
/*
curl -X POST "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions" ^
-H "Authorization: Bearer API KEY HERE" ^
-H "Content-Type: application/json" ^
-d "{\"model\": \"qwen-plus\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"Who are you?\"}]}"
*/
package com.example.demo;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import io.modelcontextprotocol.client.McpSyncClient;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner chatBot(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {

		return args -> {
			
			// Build chat client
			var chatClient = chatClientBuilder
                    .defaultSystem("You are useful assistant with several tools available, including weather and stock data.")
                    .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
					.build();

			// Setup conversation history
			java.util.Scanner scanner = new java.util.Scanner(System.in);
			java.util.ArrayList<String> conversation = new java.util.ArrayList<>();
			System.out.println("\nType your message and press Enter. Type 'exit' to quit.\n");

			while (true) {
				// Prompt user for input
				System.out.print(">>> USER: ");
				String userInput = scanner.nextLine();
				
				// Exit condition
				if (userInput == null || userInput.trim().equalsIgnoreCase("exit")) {
					break;
				}

				// Add user input to conversation history
				conversation.add("user: " + userInput);

				// Build the prompt from conversation history
				try {
					String prompt = String.join("\n", conversation);
					String response = chatClient.prompt(prompt).call().content();
					System.out.println("\n>>> ASSISTANT: " + response + "\n");
					conversation.add("assistant: " + response);
				} catch (Exception e) {
					System.err.println("Error communicating with AI service: " + e.getMessage());
					e.printStackTrace();
				}
			}
			scanner.close();
		};
	}
}