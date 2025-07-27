package br.com.lucas.sqs_learning.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class Consumer {
    private final static Logger LOGGER = getLogger(Consumer.class);
    @SqsListener("fila-teste")
    public void listen(String message) {
        LOGGER.info("Message consumed: " + message);
    }
}
