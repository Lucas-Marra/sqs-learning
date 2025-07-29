package br.com.lucas.sqs_learning.model;

import java.time.LocalDateTime;

public record Message(String content, LocalDateTime dateTime) {
}
