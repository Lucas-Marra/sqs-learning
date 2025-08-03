package br.com.lucas.sqs_learning.consumer;

import br.com.lucas.sqs_learning.model.MessageDto;
import br.com.lucas.sqs_learning.repository.MessageRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class Consumer {
    private final static Logger LOGGER = getLogger(Consumer.class);

    @Autowired
    private MessageRepository messageRepository;

    @SqsListener("test-queue")
    public void listen(MessageDto message) {
        messageRepository.save(message.toModel());
        LOGGER.info("Message consumed and saved on database: " + message);
    }
}
