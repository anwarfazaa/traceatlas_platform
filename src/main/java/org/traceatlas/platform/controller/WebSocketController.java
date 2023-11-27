package org.traceatlas.platform.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class WebSocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
    }

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

