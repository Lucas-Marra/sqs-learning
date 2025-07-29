package br.com.lucas.sqs_learning.model;

import java.time.LocalDateTime;

public record MessageDto(String content, LocalDateTime dateTime) {
    public Message toModel() {
        return new Message(dateTime, content);
    }
}
