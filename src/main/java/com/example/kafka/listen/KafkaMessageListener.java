package com.example.kafka.listen;

import com.example.kafka.domain.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "test", groupId = "test-consumer-group")
    public void listen(Message message) {
        System.out.println("接收消息: {}" + message);
    }
}