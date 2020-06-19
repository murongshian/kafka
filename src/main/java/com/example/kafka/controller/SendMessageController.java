package com.example.kafka.controller;

import com.example.kafka.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    public SendMessageController(KafkaTemplate<String,Message> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("send/{message}")
    public void send(@PathVariable String message) {
        ListenableFuture<SendResult<String, Message>> future = kafkaTemplate.send("test", new Message("慕容十安",message));
        future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
            @Override
            public void onSuccess(SendResult<String, Message> result) {
                System.out.println("成功发送消息：{}，offset=[{}]" + message + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("消息：{} 发送失败，原因：{}" + message + ex.getMessage());
            }
        });
    }
}