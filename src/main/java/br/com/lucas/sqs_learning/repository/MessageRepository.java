package br.com.lucas.sqs_learning.repository;

import br.com.lucas.sqs_learning.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
