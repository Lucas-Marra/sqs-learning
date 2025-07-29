package br.com.lucas.sqs_learning.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime creationDate;

    private String content;

    public Message() { }

    public Message(LocalDateTime creationDate, String content) {
        this.creationDate = creationDate;
        this.content = content;
    }
}
