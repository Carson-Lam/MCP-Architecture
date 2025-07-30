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

			var chatClient = chatClientBuilder
					.defaultToolCallbacks(tools)
					.build();

			System.out.println("\n>>> QUESTION: " + userInput);
			System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());

			context.close();
		};
	}
}