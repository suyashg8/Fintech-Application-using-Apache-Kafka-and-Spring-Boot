package com.project.ClientRequestAppKafka.services;

import com.google.gson.Gson;
import com.project.ClientRequestAppKafka.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public NotificationService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageThroughKafka(UserDTO userDTO){
        Gson gson = new Gson();
        kafkaTemplate.send("userData",gson.toJson(userDTO));
    }
}
