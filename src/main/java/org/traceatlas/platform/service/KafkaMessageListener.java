package org.traceatlas.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;



@Service
public class KafkaMessageListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "frontend", groupId = "public-users")
    public void listenKafkaMessages(String message,String userID) {
        messagingTemplate.convertAndSend("/frontend/" + userID,message);
    }


}
