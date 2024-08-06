package com.project.LendingEngineAppKafka.config;


import com.google.gson.Gson;
import com.project.LendingEngineAppKafka.model.User;
import com.project.LendingEngineAppKafka.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    private final UserRepository userRepository;

    @Autowired
    public KafkaConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "userData", groupId = "myGroup")
    public void parseUserData(String user){
        LOGGER.info("Received User Data :"+user);
        Gson gson =new Gson();
        User userData = gson.fromJson(user,User.class);
        userRepository.save(userData);

    }
}
