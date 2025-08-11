package br.com.lucas.sqs_learning.controller;

import br.com.lucas.sqs_learning.model.MessageDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ProducerController {
    @Autowired
    private SqsTemplate sqsTemplate;

    @Value("${sqs.host.url}")
    private String sqsHost;

    private static Logger LOGGER = getLogger(ProducerController.class);

    @PostMapping("/message-producer")
    public ResponseEntity<?> messageProducer(@RequestBody MessageDto message) {
        sqsTemplate.send(String.format("https://%s:4566/000000000000/test-queue", sqsHost), message);
        LOGGER.info("Message sent to queue");

        return ResponseEntity.ok().build();
    }
}
