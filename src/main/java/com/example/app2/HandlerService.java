package com.example.app2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class HandlerService {

    private static final Logger log = LoggerFactory.getLogger(HandlerService.class);
    private final ObjectMapper mapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final MessageService messageService;

    public HandlerService(ObjectMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }


    @Bean
    public Function<Message, Answer> input(){
        return this::handleMessage;
    }

    public Answer handleMessage(Message message) {
        try {
            log.info("Получено " + mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        var time = LocalDateTime.now().format(formatter);
        Answer answer;
        try {
            messageService.register(message);
            answer = new Answer(message.getId(), State.VALID, time, "Данные прошли валидацию");
        } catch (Exception e) {
            answer = new Answer(message.getId(), State.INVALID, time, e.getMessage());
        }
        try {
            log.info("Отправлено " + mapper.writeValueAsString(answer));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return answer;
    }
}
