package com.example.app2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

@SpringBootApplication
public class App2Application {


	public App2Application(ObjectMapper mapper, MessageService messageService, StreamBridge streamBridge) {
		this.mapper = mapper;
		this.messageService = messageService;
		this.streamBridge = streamBridge;
	}

	public static void main(String[] args) {
		SpringApplication.run(App2Application.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(App2Application.class);
	private final ObjectMapper mapper;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private final MessageService messageService;


	@Bean
	public Consumer<Message> input(){
		return message -> {
			try {
				 listen(message);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
		};
	}
	private final StreamBridge streamBridge;

	public Answer listen(Message message) throws JsonProcessingException {
		log.info("Получено " + mapper.writeValueAsString(message));
		var time = LocalDateTime.now().format(formatter);
		Answer answer;
		try {
			messageService.register(message);
			answer = new Answer(message.getId(), State.VALID, time, "Какое-то сообщенеи");
		} catch (Exception e) {
			log.error(e.getMessage());
			answer = new Answer(message.getId(), State.INVALID, time, "Сообщение для невалидного сообщения))");
		}
		streamBridge.send("output-out-0", answer);
		log.info("Отправлено " + mapper.writeValueAsString(answer));
		return answer;
	}

}
