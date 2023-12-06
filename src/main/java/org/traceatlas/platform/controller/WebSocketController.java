package org.traceatlas.platform.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RestController
public class WebSocketController {

    private final SimpMessagingTemplate template;
    private final Map<String, String> clientSessionMapping;

    @Autowired
    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
        this.clientSessionMapping = new ConcurrentHashMap<>();
    }

    // Register client information in the HashMap
    public void addClientInfo(String sessionId, String clientId) {
        this.clientSessionMapping.put(sessionId, clientId);
    }

    public void sendDataToClient(String sessionID, String destination, Object payload){
        String clientId = clientSessionMapping.get(sessionID);
        if (clientId != null) {
            // Sending data to a the required dashboard by making sure client ID is not empty
            // currently clientID = dashboardID
            this.template.convertAndSendToUser(clientId,  destination, payload);
        }
    }


    // Will be deprecated
    @MessageMapping("/send/message")
    public void sendMessage(String message){
        System.out.println(message);
        this.template.convertAndSend("/message",  "Hello, " + message);
    }

    // Added Path Variable for topic name
    // each topic should be a dashboard
    @PostMapping("/scriptainer_data")
    public String sendMsg(@RequestBody String message){
        template.convertAndSend("/topic/message", message);
        return "Message sent";
    }

}

